package com.TaskManagement.SpringBoot.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // مدة صلاحية التوكن (يوم واحد بالمللي ثانية)
    private static final long EXPIRATION_TIME = 86400000;

    // توليد مفتاح صالح بشكل تلقائي باستخدام خوارزمية HS256
    // هذا يضمن أن المفتاح له الطول والصيغة المطلوبة لتوقيع التوكن
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * توليد توكن JWT باستخدام البريد الإلكتروني والدور.
     * @param email بريد المستخدم
     * @param role  دور المستخدم
     * @return توكن JWT موقّع
     */
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * استخراج البريد الإلكتروني من التوكن.
     * @param token توكن JWT
     * @return البريد الإلكتروني للمستخدم الموجود في التوكن
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * التحقق من صلاحية التوكن.
     * @param token توكن JWT
     * @return true إذا كان التوكن صالحًا، وإلا false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            // يمكن هنا تسجيل الخطأ لمزيد من التحليل عند الحاجة
            return false;
        }
    }
}
