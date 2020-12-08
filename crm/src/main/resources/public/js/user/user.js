layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    /**
     * 用户列表展示
     */
    var  tableIns = table.render({
        elem: '#userList',
        url : ctx + '/user/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: "id", title:'编号',fixed:"true", width:80},
            {field: 'userName', title: '用户名', minWidth:50, align:"center"},
            {field: 'email', title: '用户邮箱', minWidth:100, align:'center'},
            {field: 'phone', title: '用户电话', minWidth:100, align:'center'},
            {field: 'trueName', title: '真实姓名', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center',minWidth:150},
            {field: 'updateDate', title: '更新时间', align:'center',minWidth:150},
            {title: '操作', minWidth:150, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });
    /**
     * 绑定搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        table.reload('userListTable', {
            where: { //设定异步数据接口的额外参数，任意设
                userName: $("input[name='userName']").val(), // 客户名
                email: $("input[name='email']").val(), // 创建人
                phone: $("input[name='phone']").val() // 开发状态
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        }); // 只重载数据
    });
    /**
     * 头部工具栏事件
     */
    table.on("toolbar(users)", function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateUserDialog();
                break;
            case "del":
                del(checkStatus.data,0);
                break;
        }
    });
    /**
     * 行监听事件
     */
    table.on("tool(users)", function(obj){
        var layEvent = obj.event;
// 监听编辑事件
        if(layEvent === "edit") {
            openAddOrUpdateUserDialog(obj.data.id);
        }else {
            del(obj.data.id,1);
        }
    });
    /**
     * 打开用户添加或更新对话框
     */
    function openAddOrUpdateUserDialog(userId) {
        var url = ctx + "/user/addOrUpdateUserPage";
        var title = "用户管理-用户添加";
        if (userId!=null) {
            url = url + "?id=" + userId;
            title = "用户管理-用户更新";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["650px", "400px"],
            maxmin: true,
            content: url
        });
    }
    //删除
    /**
     * 删除用户
     * @param inData
     * @param loc
     */
    function del(inData,loc) {
        if (loc==0){
            console.log(inData);
            //头
            layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){
                //do something
                var ids="";
                for (var i=0;i<inData.length;i++){
                    if (i ==0){
                        ids+="?id="+inData[i].id;
                    }else {
                        ids+="&id="+inData[i].id;
                    }

                }
                $.get(ctx+"/user/leaf"+ids,function (MSG) {
                    if (MSG.code==200){
                        layer.msg(MSG.msg,{icon: 1});
                        tableIns.reload();
                    }else {
                        layer.msg("失败"+MSG.msg,{icon: 5});
                    }
                });
                layer.close(index);
            });
        }else {
            console.log(inData);
          //行
            layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){
                //do something
                $.get(ctx+"/user/leaf",{id:inData},function (MSG) {
                  if (MSG.code==200){
                      layer.msg("成功",{icon: 1});
                      tableIns.reload();
                  }else {
                      layer.msg("失败",{icon: 5});
                  }
                });
                layer.close(index);
            });

        }
    }
});