package com.hyd.fx.components;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FilterableListViewTest extends Application {

    public static final List<String> STRINGS = Arrays.asList(
        "兴盛社区网络服务店", "芙蓉兴盛（昶亮食品店）", "开福万国城店", "开福区汉回村店", "开福天井社区店", "开福沙坪店", "青竹湖植基店", "开福新安农贸店",
        "金霞安置店", "开福太阳山店", "望城兴城一店", "开福双桥村店", "桥驿营蒲塘店", "开福捞刀河店", "捞刀河安置店", "开福龙潭小区店", "开福竹隐村店",
        "开福悦湖山悦湖山芙蓉兴盛超市", "星沙花样年华店", "星沙灰埠小区店", "长沙县深远路店", "星沙灰埠社区店", "星沙灰埠安置店", "星沙蓝色海岸店",
        "星沙东方航标店", "星沙诺亚山林店", "星沙松雅小区店", "星沙深业睿城店", "星沙松雅星宇店", "星沙楚天雅郡店", "星沙当代广场店", "铁建国际城店",
        "黄花华湘安置店", "长沙盛地领航店", "星沙早安星城店", "星沙楚天馨苑店", "星沙楚天世纪城店", "陈家湖社区店", "开福下大垅店", "开福万煦园店",
        "开福蚌塘小区店", "开福四方锦城店", "开福金帆小区店", "开福威尔士春天店", "开福雄新华府店", "开福双湾国际店", "开福湖湘文化店", "开福德峰小区店",
        "开福中坤领袖峰店", "开福豪江苑店", "开福恒鑫澜北湾店", "开福留芳岭店", "开福大王家巷店", "天心童话里店", "黄兴蓝田新村店", "芙蓉西南明苑店",
        "芙蓉火炬四片店", "芙蓉金域蓝湾店", "芙蓉新合一村店", "雨花上河国际店", "雨花凯乐湘园店", "芙蓉新桥小区店", "芙蓉西子花苑店", "雨花万家城店",
        "雨花盛世华章二店", "雨花双水湾店", "2.3测试用门店", "开福东兴园巷店", "雨花火焰高桥店", "开福区融江苑", "省委店", "星沙紫晶城店", "芙蓉世嘉国际店",
        "开福珠江郦城谭田乃店", "长沙县黄兴丽景鑫城", "星沙甘木塘生活超市", "晓熙食品店", "开福福兴阁店", "雨花东澜湾店", "芙蓉荷花园店", "开福区金泰路店",
        "开福铁炉寺店", "长沙县泉塘亦山食品店", "开福李家村店", "德雅村店", "星沙长丰星城店", "尚都美寓店", "开福卧琥城兴盛店", "芙蓉鑫湘佳园店",
        "开福罗汉庄村店", "星沙松雅轩店", "开福海通店", "芙蓉鲲鹏食品店", "芙蓉金科市场店", "农科家园店", "星沙泉塘小区店", "开福万源炒货店", "星沙草莓街店",
        "开福共联社区店", "湘伊缘店", "开福荷花池店", "星沙板仓北路店", "惠宾食品店", "星沙未来蜂巢兴盛0519店", "雨花紫薇鑫园店", "开福江滨社区店",
        "袁小叶食品店", "开福四季美景店", "芙蓉银港水晶城店", "开福学堂路店", "易文志食品店", "开福刘佳食品店", "开福区湘春路店", "开福君悦香邸店",
        "鑫荣水果超市", "星沙海德公园店", "开福水韵花都店", "上城嘉苑店", "芙蓉桐荫里店", "雨花合丰安置店", "芙蓉蓉园路店", "星沙商业乐园店", "开福大星村店",
        "恒大翡翠华庭店", "大众传媒店", "芙蓉新世纪家园店", "山水豪园店", "程程批发部", "薄利生活超市", "开福洪西安置店", "雨花车站南路店", "开福湘粤小区店",
        "赏江苑春一便利店", "骞华食品店", "沙坪七七超市", "星沙盛地春天里店", "开福浏河村店", "开福白霞村店", "芙蓉高岭小区盼圆店", "长沙县爱琴海岸店",
        "芙蓉九道湾店", "雨花汇都公寓店", "货特真便利店", "雨花三湘小区店", "长沙县晨轩店", "雨花盛世华章店", "开福金霞小区店", "开福极目楚天家园店",
        "泉塘圣力华苑店", "芙蓉同丰街店", "开福涝湖二期店", "梦泽园店", "世纪城悦江苑店", "佳兆时代广场店", "星沙爵士湘店", "芙蓉区德政园店",
        "芙蓉湘遇熙岸店", "省财政厅店", "开福万象公馆店", "开福月湖市场店", "榔梨蓝思二期店", "中南汽车世界店", "星沙筑梦园"
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FilterableListView<String> listView = new FilterableListView<>();
        listView.setOriginalItems(STRINGS);

        TextField textField = new TextField();
        textField.textProperty().addListener((_ob, _old, _new) -> {
            boolean empty = _new.trim().length() == 0;
            listView.filter(s -> {
                if (empty) {
                    return true;
                }

                return s.contains(_new);
            });
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(textField);
        borderPane.setCenter(listView);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }
}