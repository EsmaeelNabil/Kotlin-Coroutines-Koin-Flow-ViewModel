package com.esmaeel.anim.ApiManagerDefault;


import java.io.Serializable;
import java.util.List;

public class CountriesResponse extends ResponseHolder{

    /**
     * status : true
     * msg : ["Success Request"]
     * data : [{"id":1,"country_name":"مصر","phone_code":"0020"},{"id":2,"country_name":"الكويت","phone_code":"00965"}]
     */

    private boolean status;
    private List<String> msg;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * country_name : مصر
         * phone_code : 0020
         */

        private int id;
        private String country_name;
        private String phone_code;
        private String phone_length;
        private String phone_min_length;
        private String flag_icon;
        private String currency_id;

        public String getPhone_length() {
            return phone_length;
        }

        public void setPhone_length(String phone_length) {
            this.phone_length = phone_length;
        }

        public String getPhone_min_length() {
            return phone_min_length;
        }

        public void setPhone_min_length(String phone_min_length) {
            this.phone_min_length = phone_min_length;
        }

        public String getFlag_icon() {
            return flag_icon;
        }

        public void setFlag_icon(String flag_icon) {
            this.flag_icon = flag_icon;
        }

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public void setPhone_code(String phone_code) {
            this.phone_code = phone_code;
        }
    }
}
