package com.yugugugu.client.application;

import com.yugugugu.view.chat.IChatMethod;
import com.yugugugu.view.login.ILoginMethod;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
public class UIService {
    private ILoginMethod login;
    private IChatMethod chat;
}
