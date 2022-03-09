package com.haohan.platform.service.sys.modules.weixin.mp.user;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgw on 2018/11/5.
 */
@Service
public class WxMpUserManageService {

    public List<WxMpUser> fetchSubscribeUser(WxMpService wxMpService){

        try {

            List<String> openIds = new ArrayList<>();
            WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
            openIds.addAll(wxMpUserList.getOpenids());

            WxMpUserService wxMpUserService =  wxMpService.getUserService();

            if(wxMpUserList.getTotal()>10000){
                //暂定20000关注用户
               wxMpUserList = wxMpUserService.userList(wxMpUserList.getNextOpenid());
                openIds.addAll(wxMpUserList.getOpenids());
            }

            List<WxMpUser> wxMpUsers = new ArrayList<>();
            int defaultSize = 100;
            int m = (openIds.size()%defaultSize == 0)?openIds.size()/defaultSize:openIds.size()/defaultSize+1;
            //关注人数小于100
            if(defaultSize>openIds.size()){
                defaultSize = openIds.size()-1;
                m=1;
            }
            for(int j=0 ;j<m;j++){
                if(0 == j){
                    List<WxMpUser> userList = wxMpUserService.userInfoList(openIds.subList(0,defaultSize));
                    wxMpUsers.addAll(userList);
                }else{
                    int maxSize =(openIds.size() > (j+1)*defaultSize)?(j+1)*defaultSize:openIds.size();
                  List<String> list =  openIds.subList(j*defaultSize,maxSize);
                    List<WxMpUser> userList = wxMpUserService.userInfoList(list);
                    wxMpUsers.addAll(userList);
                }
            }

            return wxMpUsers;

        } catch (WxErrorException e) {
            e.printStackTrace();
        }

return null;
    }
}
