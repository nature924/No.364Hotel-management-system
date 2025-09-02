<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <h3 class="title">酒店管理系统后台登录</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" name="username" type="text" auto-complete="on" placeholder="请输入用户名">
          <template slot="prepend">
            <span class="svg-container">
              <svg-icon icon-class="user" />
            </span>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          placeholder="请输入密码"
          @keyup.enter.native="handleLogin">
          <template slot="prepend">
            <span class="svg-container">
              <svg-icon icon-class="password" />
            </span>
          </template>
          <span class="show-pwd" @click="showPwd">
            <svg-icon icon-class="eye" />
          </span>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="" style="width:100%;" @click.native.prevent="login">
          登 录
        </el-button>
      </el-form-item>
      <div class="tips"/>
    </el-form>
  </div>
</template>

<script>
import { isvalidUsername } from '@/utils/validate'
// eslint-disable-next-line no-unused-vars
import { getByUsername } from '../../api/admin'
import { login } from '../../api/login'
import Cookies from 'js-cookie'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePass = (rule, value, callback) => {
      if (value.length < 1) {
        callback(new Error('密码不能为空'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch((err) => {
            this.loading = false
            this.$message.error(err)
          })
        } else {
          console.log('error submit!!')
        }
        this.loading = false
      })
    },
    login() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          const username = this.loginForm.username.trim()
          this.loading = true
          login(this.loginForm).then(response => {
            const res = response
            if (res.code === 1000) {
              Cookies.set('admin_name', username)
              Cookies.set('session_id', res.data.sessionId)
              Cookies.set('admin_id', res.data.userId)
              Cookies.set('role', res.data.role)
              this.$router.push({ path: this.redirect || '/' })
            } else {
              this.$message.warning('用户名或密码错误！请检查后再试')
            }
          }).catch(err => {
            this.$message.error('用户名或密码错误！请检查后再试')
            console.error(err)
          }).finally(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$light_blue: #ADD8E6; // Define light blue color
$light_gray: #f5f5f5; // Define light gray color
$white: #fff; // Define white color

/* reset element-ui css */
.login-container {
  height: 100vh;
  width: 100vw;
  background-color: $light_blue; /* Set entire background to light blue */
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-form {
  background-color: $light_gray; /* Set login form background to light gray */
  border-radius: 10px;
  padding: 30px;
  width: 400px; /* Adjust login form width */
}

.el-input {
  input {
    background-color: $white; /* Set input fields to white */
  }
}

.svg-container {
  color: #889aa4; /* Adjust icon color */
  margin-right: 10px; /* Add margin between icon and input field */
}

.title {
  text-align: center; /* Center the title */
  margin-bottom: 20px; /* Add some space below the title */
}
</style>
