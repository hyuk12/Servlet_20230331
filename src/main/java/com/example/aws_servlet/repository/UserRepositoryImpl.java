package com.example.aws_servlet.repository;

import com.example.aws_servlet.entity.User;
import com.example.aws_servlet.util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepositoryImpl implements UserRepository {
    // Repository 객체 싱글톤 정의
    private static UserRepository instance;
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    private final DBConnectionMgr pool;

    private UserRepositoryImpl() {
        // DBConnectionMgr DI (Dependency Injection)
        pool = DBConnectionMgr.getInstance();
    }

    @Override
    public int save(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int successCount = 0;
        try {
            conn = pool.getConnection();
            String sql = "insert into user_mst(username, password, name, email) values(?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());

            successCount = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pool.freeConnection(conn, pstmt);
        }
        return successCount;
    }

    @Override
    public User findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = pool.getConnection();
            String sql = "select\n" +
                    "\tum.user_id,\n" +
                    "    um.username,\n" +
                    "    um.password,\n" +
                    "    um.name,\n" +
                    "    um.email,\n" +
                    "    ud.gender,\n" +
                    "    ud.birthday,\n" +
                    "    ud.address\n" +
                    "from\n" +
                    "\tuser_mst um\n" +
                    "left outer join user_dtl ud on(ud.user_id = um.user_id)" +
                    "where\n" +
                    "\tum.username =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                user = User
                        .builder()
                        .userId(rs.getInt(1))
                        .username(rs.getString(2))
                        .password(rs.getString(3))
                        .name(rs.getString(4))
                        .email(rs.getString(5))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return user;
    }
}
