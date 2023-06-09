package com.example.aws_servlet.repository;

import com.example.aws_servlet.entity.Role;
import com.example.aws_servlet.util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleRepositoryImpl implements RoleRepository{
    // Repository 객체 싱글톤 정의
    private static RoleRepository instance;
    public static RoleRepository getInstance() {
        if(instance == null) {
            instance = new RoleRepositoryImpl();
        }
        return instance;
    }

    // DBConnectionMgr DI
    private DBConnectionMgr pool;

    private RoleRepositoryImpl() {
        pool = DBConnectionMgr.getInstance();
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Role role = null;

        try {
            con = pool.getConnection();
            String sql = "select role_id, role_name from role_mst where role_name = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, roleName);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                role = Role.builder()
                        .roleId(rs.getInt(1))
                        .roleName(rs.getString(2))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }

        return role;
    }
}
