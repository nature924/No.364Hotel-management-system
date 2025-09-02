package cn.cow.hotel.controller.worker;

import cn.cow.hotel.entity.RoomGoods;
import cn.cow.hotel.response.AjaxResult;
import cn.cow.hotel.response.ResponseTool;
import cn.cow.hotel.service.RoomGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author
 * @version 1.0
 * @ClassName RoomGoodsController
 * @Description TODO
 * @createTime 2023-3-19 10:20
 */
@RestController
@RequestMapping(value = "/op/roomGoods")
public class RoomGoodsController {

    @Autowired
    private RoomGoodsService roomGoodsService;

    @GetMapping("/getById")
    public AjaxResult getById(Integer id){
        return ResponseTool.success(roomGoodsService.getById(id));
    }

    @RequestMapping("/selectAll")
    public AjaxResult selectAll(@RequestParam(value = "searchData",required = false) String searchData){
        return ResponseTool.success(roomGoodsService.selectAll(searchData));
    }

    @PostMapping("/insert")
    public AjaxResult insert(String name,String remark){
        RoomGoods roomGoods=new RoomGoods();
        roomGoods.setName(name);
        roomGoods.setRemark(remark);
        roomGoodsService.insert(roomGoods);
        return ResponseTool.success("新增成功");
    }

    @PostMapping("/update")
    public AjaxResult update(Integer id,String name,String remark){
        RoomGoods roomGoods=new RoomGoods();
        roomGoods.setName(name);
        roomGoods.setRemark(remark);
        roomGoods.setId(id);
        roomGoodsService.update(roomGoods);
        return  ResponseTool.success("更新成功");
    }

    @PostMapping("/delete")
    public AjaxResult delete(Integer id){
        roomGoodsService.delete(id);
        return ResponseTool.success("删除成功");
    }

}
