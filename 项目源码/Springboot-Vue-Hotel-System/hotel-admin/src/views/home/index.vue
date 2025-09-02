<template>
  <div id="home">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>欢迎使用酒店管理系统</span>
          </div>
          <el-button type="" @click="dialogFormVisible = true">爱国、敬业、诚信、友善</el-button>
          <!--          <el-button type="">退房结账</el-button>-->
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>当前日期</span>
          </div>
          <p>{{ new Date() | formatDay }}</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>数据展示</span>
          </div>
          <div>系统已注册用户人数： <el-tag>{{ userCount }}</el-tag></div>
          <div>系统已接受订单数量： <el-tag>{{ orderCount }}</el-tag></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>客房住宿率</span>
          </div>
          <div id="liveRate" style="left:0;width: 100%;height: 250px"/>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>房型价格</span>
          </div>
          <div id="roomType" style="left:0;width: 100%;height: 250px"/>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :visible.sync="dialogFormVisible" title="社会主义核心价值观">
      <span>社会主义核心价值观是中共十八大提出的社会主义核心价值体系的高度凝练和集中表达，是现阶段全国人民对社会主义核心价值观具体内容的最大公约数的表述。社会主义核心价值观明确了国家、社会、公民三个层面的价值目标、价值取向、价值准则，是推进社会主义核心价值体系建设，用社会主义核心价值体系引领社会思潮、凝聚社会共识的重要理论和实践基础。社会主义核心价值观倡导富强、民主、文明、和谐，自由、平等、公正、法治，爱国、敬业、诚信、友善。</span>
      <!--      <el-form :model="form" inline>-->
      <!--        <el-form-item label="预订人姓名" >-->
      <!--          <el-input v-model="form.name" auto-complete="on"/>-->
      <!--        </el-form-item>-->
      <!--        <el-form-item label="预留手机号" >-->
      <!--          <el-input v-model="form.phone" auto-complete="on"/>-->
      <!--        </el-form-item>-->
      <!--        <el-form-item>-->
      <!--          <el-button type="" @click="findOrder()">查询</el-button>-->
      <!--        </el-form-item>-->
      <!--      </el-form>-->
      <div v-if="isOrderShown && order!=null">
        <el-table
          ref="singleTable"
          :data="order"
          highlight-current-row
          style="width: 100%"
          @current-change="handleCurrentChange">
          <el-table-column
            property="orderId"
            label="订单号"
            width="120"/>
          <el-table-column
            property="orderType"
            label="预订方式"
            width="120"/>
          <el-table-column
            property="roomType"
            label="预订房型"
            width="120"/>
          <el-table-column
            property="orderDate"
            label="预订日期"
            width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.orderDate | formatDay }}</span>
            </template>
          </el-table-column>
          <el-table-column
            property="orderDays"
            label="预订天数"/>
          <el-table-column
            property="orderCost"
            label="预订费用"/>
        </el-table>
      </div>
      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button @click="cancelIn()">取 消</el-button>-->
      <!--        <el-button type="" @click="checkIn()">确定入住</el-button>-->
      <!--      </div>-->
    </el-dialog>
  </div>
</template>

<script>
import { getOrderCount, getOccupancyRateData } from '../../api/order'
import { getUserCount } from '../../api/user'
import { checkIn } from '../../api/checkIn'
import { getAllRoomType } from '@/api/roomType'

export default {
  name: 'Home',
  data: function() {
    return {
      roomNumber: null,
      dialogFormVisible: false,
      userCount: 1203,
      orderCount: 12032,
      form: {
        name: '',
        phone: ''
      },
      isOrderShown: false,
      order: [],
      selectOrder: {},
      inId: '',
      occupancyRateData: [],
      roomTypeData: [],
      searchData: ''
    }
  },
  created: function() {
    this.getCount()
    this.fetchOccupancyRateData()
    this.fetchData()
  },
  mounted: function() {
    this.rtChart()
    // 注意：将渲染图表的逻辑放到获取数据成功的回调函数中
  },
  methods: {
    handleCurrentChange(data) {
      this.selectOrder = data
    },
    getCount() {
      getUserCount().then(response => {
        this.userCount = response.data
      })
      getOrderCount().then(res => {
        this.orderCount = res.data
      })
    },
    rtChart() {
      var rtChart = this.$echarts.init(document.getElementById('roomType'), 'light')
      rtChart.setOption({
        tooltip: {},
        series: [{
          name: '价格',
          type: 'pie',
          radius: ['30%', '60%'],
          data: this.roomTypeData.map(item => ({
            value: item.price,
            name: item.roomType
          }))
        }]
      })
    },
    fetchOccupancyRateData() {
      getOccupancyRateData().then(response => {
        this.occupancyRateData = response.data
        console.log('Occupancy rate data fetched successfully:', this.occupancyRateData)
        // 数据获取成功后渲染图表
        this.lrChart()
      }).catch(error => {
        console.error('Error fetching occupancy rate data:', error)
      })
    },
    lrChart() {
      var lrChart = this.$echarts.init(document.getElementById('liveRate'), 'light')
      lrChart.setOption({
        xAxis: {
          type: 'category',
          data: this.occupancyRateData.map(item => item.month),
          axisLabel: {
            formatter: '{value} 月'
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} %'
          }
        },
        series: [{
          data: this.occupancyRateData.map(item => item.occupancyRate),
          type: 'line'
        }]
      })
    },
    fetchData() {
      this.listLoading = true
      getAllRoomType(this.searchData).then(response => {
        this.roomTypeData = response.data
        this.listLoading = false
        this.rtChart()// 获取数据成功后重新渲染饼图
      }).catch(error => {
        console.error('Error fetching room type data:', error)
        this.listLoading = false
      })
    }, search() { this.fetchData() },
    cancelIn() {
      this.dialogFormVisible = false
      this.order = null
      this.isOrderShown = false
      this.selectOrder = null
    },
    checkIn() {
      if (this.selectOrder == null) {
        this.$message.warning('请选择入住的订单')
        return
      }
      this.dialogFormVisible = false
      checkIn({ orderId: this.selectOrder.orderId }).then(res => {
        if (res.code !== 1000) {
          this.$message.warning(res.message)
        } else {
          this.$message.success('入住成功')
        }
      })
      this.order = null
      this.isOrderShown = false
    }
  }
}
</script>

<style scoped>

  .el-row {
    margin: 10px 5px;
  }
  .el-card{
    text-align: center;
  }
</style>
