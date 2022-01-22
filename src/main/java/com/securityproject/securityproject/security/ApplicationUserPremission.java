package com.securityproject.securityproject.security;

public enum ApplicationUserPremission {

    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String premission;


    ApplicationUserPremission(String premission) {
        this.premission = premission;
    }

    public String getPremission() {
        return premission;
    }
}
