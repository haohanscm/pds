<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <title>营业额</title>
    <script src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            resetTip();
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            var dataList = {
                date: formatData('${dateList}') || [],
                data: formatData('${amountList}') || [0, 0, 0, 0]
            }
            // 指定图表的配置项和数据
            var option = {
                xAxis: {
                    type: 'category',
                    name: '日期',
                    boundaryGap: false,
                    data: dataList.date
                },
                yAxis: {
                    name:'金额',
                    type: 'value'
                },
                series: [{
                    data: dataList.data,
                    type: 'line',
                    smooth: true,
                    areaStyle: {}
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

            function formatData (str) {
                var list = str.slice(1, -2).split(',')
                list = list.map(function(item) {
                    return item.trim()
                })
                console.log(str)
                return list.reverse()
            }

            // function getVirtulData(value) {
            //     var today = new Date().getTime()
            //     var dayTime = 3600 * 24 * 1000;
            //
            //     var date = today - dayTime * value
            //     var end = today + dayTime * value
            //     var data = [];
            //     for (var time = date; time < end; time += dayTime) {
            //         data.push(echarts.format.formatTime('dd', time))
            //     }
            //     return data;
            // }

            function sliceArray(value, data) {
                if (!Array.isArray(data)) return;
                if (value > data.length) {
                    value = data.length
                }
                return data.slice(-value)
            }
            $('.btn').click(function(e) {
                if ($(this).hasClass('active')) return
                var target = e.target
                var value = target.value
                $( ".btn" ).toggleClass('active', false);
                $(this).addClass('active');
                option.xAxis.data = sliceArray(value, dataList.date)
                option.series[0].data = sliceArray(value, dataList.data)
                myChart.setOption(option);
            })
        });
    </script>
    <style type="text/css">
        .well{
            background-color:#fff;
            padding: 20px;
            margin-bottom: 10px;
        }
        body {
            background-color:#f3f3f4;
        }
        .itembox>ul{
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
        .itembox>ul>li{
            width: 24%;
            border: 1px solid #f5f5f5;
            list-style: none;
        }
        .item+.item {
            margin-left: 10px;
        }
        .item-title{
            height: 38px;
            background: #f4f5fa;
            font-size: 14px;
            color: #0e0e0e;
            line-height: 38px;
            text-align: center;
        }
        .item-cont{
            height: 50px;
            font-size: 20px;
            color: #333;
            text-align: center;
            line-height: 50px;
            border: 1px solid #e7e7eb;
        }
        .btn-group {
            margin: 15px 0;
        }
    </style>
</head>
<body>
<div class="well">
    <h4 style="margin-bottom:20px;">合作商信息</h4>
    <div class="itembox">
        <ul>
            <li class="item">
                <div class="item-title">合作商名称</div>
                <div class="item-cont">${agentMange.name}</div>
            </li>
            <li class="item">
                <div class="item-title">合同签署日期</div>
                <div class="item-cont"><fmt:formatDate value="${agentMange.contractDate}" pattern="yyyy年MM月dd日"/></div>
            </li>
            <li class="item">
                <div class="item-title">合作商状态</div>
                <div class="item-cont">${fns:getDictLabel(agentMange.status,"agent_status","")}</div>
            </li>
            <li class="item">
                <div class="item-title">分润比例</div>
                <div class="item-cont">${agentMange.profit}</div>
            </li>
        </ul>
    </div>
</div>
<div class="well">
    <h4 style="margin-bottom:20px;">商户信息统计</h4>
    <div class="itembox">
        <ul>
            <li class="item">
                <div class="item-title">商户数量</div>
                <div class="item-cont">${agentCount["merchantNum"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核通过商户数</div>
                <div class="item-cont">${agentCount["successfulMerchant"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核未通过商户数</div>
                <div class="item-cont">${agentCount["failedMerchant"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核中商户数</div>
                <div class="item-cont">${agentCount["pendingMerchant"]}</div>
            </li>
        </ul>
        <ul>
            <li class="item">
                <div class="item-title">门店数量</div>
                <div class="item-cont">${agentCount["shopNum"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核通过门店数</div>
                <div class="item-cont">${agentCount["successfulShop"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核未通过门店数</div>
                <div class="item-cont">${agentCount["failedShop"]}</div>
            </li>
            <li class="item">
                <div class="item-title">审核中门店数</div>
                <div class="item-cont">${agentCount["pendingShop"]}</div>
            </li>
        </ul>
    </div>
</div>
<sys:message content="${message}"/>
<div class="well">
    <h4 style="margin-bottom:20px;">今日交易数据</h4>
    <div class="itembox">
        <ul>
            <li class="item">
                <div class="item-title" style="background:#21bfff;color:#fff;">实际交易金额</div>
                <div class="item-cont">${turnoverToday.totalGoodsAmount}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#f5a623;color:#fff;">交易笔数</div>
                <div class="item-cont">${turnoverToday.orderCount}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#50e3c2;color:#fff;">退款金额</div>
                <div class="item-cont">${turnoverToday.refundAmount}</div>
            </li>
            <li class="item">
                <div class="item-title" style="background:#fc5555;color:#fff;">退款笔数</div>
                <div class="item-cont">${turnoverToday.refundCount}</div>
            </li>
        </ul>
    </div>
    <div class="btn-group">
        <button class="btn" value="2">今天</button>
        <button class="btn" value="8">近七天</button>
        <button class="btn" value="31">近30天</button>
    </div>
    <div id="main" style="width: 100%;height:300px;"></div>
</div>
</body>
</html>