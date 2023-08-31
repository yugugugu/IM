package com.yugugugu.server.ddd.infrastructure.common;

public class Constants {
    public enum TalkType{

        Friend(0,"好友"),
        Group(1,"群组");
        private Integer code;
        private String info;

        TalkType(Integer code,String info){
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    public enum WordType{
        BLACK,WHITE
    }

    public enum EndType {

        /**
         * 有下一个，结束
         */
        HAS_NEXT, IS_END
    }
}
