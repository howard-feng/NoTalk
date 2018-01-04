package com.notalk.view;

import com.notalk.MainApp;
import com.notalk.model.TcpClientThread;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RootLayoutController {
    private MainApp mainApp;
    private TcpClientThread tcpClientThread;
    private AnchorPane MainContentTalk ;
    private AnchorPane MainContentContacts ;
    private AnchorPane MainContentFunction ;
    private AnchorPane MainContentSetting ;
    private MainContentSettingController mainContentSettingController;
    private MainContentContactsController mainContentContactsController;
    private MainContentTalkController mainContentTalkController;
    private MainContentFunctionController mainContentFunctionController;
    private boolean isLoadedMinorSetting = false;
    private boolean isLoadedMinorMsg;

    @FXML
    private BorderPane TalkMain;

    @FXML
    private Pane Msg;

    @FXML
    private Pane People;

    @FXML
    private Pane Function;

    @FXML
    private Pane Setting;

    @FXML
    private Pane MainHead;

    @FXML
    private SplitPane TalkContent;

//    @FXML

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setTcpClientThread(TcpClientThread tcpClientThread) {
        this.tcpClientThread = tcpClientThread;
    }

    @FXML
    public void clickMsg(){
        //同级其他俩图标变为空心，自己变为选中状态
        Msg.setStyle("-fx-background-image: url('/resources/images/Menu/messageSelected.png')");
        People.setStyle("-fx-background-image: url('/resources/images/Menu/people.png')");
        Function.setStyle("-fx-background-image: url('/resources/images/Menu/function.png')");
        Setting.setStyle("-fx-background-image: url('/resources/images/Menu/setting.png')");
        TalkMain.setCenter(MainContentTalk);
    }

    @FXML
    public void clickPeople() {
        Msg.setStyle("-fx-background-image: url('/resources/images/Menu/message.png')");
        People.setStyle("-fx-background-image: url('/resources/images/Menu/peopleSelected.png')");
        Function.setStyle("-fx-background-image: url('/resources/images/Menu/function.png')");
        Setting.setStyle("-fx-background-imag:url('/resources/images/Menu/setting.png')");
        TalkMain.setCenter(MainContentContacts);
    }

    @FXML
    public void clickFunciton(){
        Msg.setStyle("-fx-background-image: url('/resources/images/Menu/message.png')");
        People.setStyle("-fx-background-image: url('/resources/images/Menu/people.png')");
        Function.setStyle("-fx-background-image: url('/resources/images/Menu/functionSelected.png')");
        Setting.setStyle("-fx-background-image: url('/resources/images/Menu/setting.png')");
        TalkMain.setCenter(MainContentFunction);
    }

    @FXML
    public void clickSetting(){
        Msg.setStyle("-fx-background-image: url('/resources/images/Menu/message.png')");
        People.setStyle("-fx-background-image: url('/resources/images/Menu/people.png')");
        Function.setStyle("-fx-background-image: url('/resources/images/Menu/function.png')");
        Setting.setStyle("-fx-background-image: url('/resources/images/Menu/settingSelecetd.png')");
        TalkMain.setCenter(MainContentSetting);

        //初次点击设置界面 加载设置子页面
        if(!isLoadedMinorSetting)
            this.mainContentSettingController.loadSetting();
    }

    /*
    * 加载四个右侧布局界面
    * 并初始界面设定为聊天界面
    *
    * */
    public void loadPane(){
        try{
            FXMLLoader talkLoader = new FXMLLoader();
            talkLoader.setLocation(MainApp.class.getResource("view/MainContentTalk.fxml"));
            MainContentTalk = (AnchorPane) talkLoader.load();
            this.mainContentTalkController = talkLoader.getController();
            mainContentTalkController.setRootLayoutController(this);
            mainContentTalkController.setClient(this.tcpClientThread);

            FXMLLoader contactsLoader = new FXMLLoader();
            contactsLoader.setLocation(MainApp.class.getResource("view/MainContentContacts.fxml"));
            MainContentContacts = (AnchorPane) contactsLoader.load();
            MainContentContactsController mainContentContactsController = contactsLoader.getController();
            mainContentContactsController.setRootLayoutController(this);

            FXMLLoader functionLoader = new FXMLLoader();
            functionLoader.setLocation(MainApp.class.getResource("view/MainContentFunction.fxml"));
            MainContentFunction = (AnchorPane) functionLoader.load();

            FXMLLoader settingLoader = new FXMLLoader();
            settingLoader.setLocation(MainApp.class.getResource("view/MainContentSetting.fxml"));
            MainContentSetting = (AnchorPane) settingLoader.load();
            this.mainContentSettingController = settingLoader.getController();
            mainContentSettingController.setMainApp(this);

        }catch (IOException e) {
            e.printStackTrace();
        }

        TalkMain.setCenter(MainContentTalk);
    }

    /*
    *
    * 初始化聊天界面
    * */
    public void initTalkInfo(HashMap<String,String> info){
        mainContentTalkController.loadInfo(info);
    }




}
