package com.example.bookclubdesktopcli;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

public class Member {

        private int id;
        private String name;
        private String gender;
        private Date birth_date;
        private Boolean banned;

        public Member(int id, String name, String gender, Date birth_date, Boolean banned) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.birth_date = birth_date;
            this.banned = banned;
        }
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public Date getBirth_date() {
            return birth_date;
        }

        public Boolean getBanned() {
            return banned;
        }
}
