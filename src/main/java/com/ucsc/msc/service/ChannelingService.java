package com.ucsc.msc.service;

import com.ucsc.msc.util.DataUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * The channeling related business functionality REST service.
 * 
 * @author Lahiru Yapa
 */
@Path("/channeling")
public class ChannelingService {
    
    //http://localhost:8080/MediChanneling/rest/channeling/show
    @GET
    @Path("/show")
    public Response showChanneling(){
        return Response.status(200).entity("Channeling REST API is function").build();
    }
    
    @GET
    @Path("{id}")
    public Response getChanneling(@PathParam("id") long id) throws SQLException{
        DataUtil dataUtil = new DataUtil();
        Connection con = null;
        Statement stm = null;
        ResultSet result = null;
        try {
            con = dataUtil.getDataConnection();
            stm = con.createStatement();
            String sqlQuery = "select * from channeling where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, id);            
            
            result = preparedStmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return Response.status(200).entity(result).build();
    }    
    
    
    @POST
    @Path("/add")
    public Response createChanneling(
            @FormParam("doctorId") long doctorId,
            @FormParam("patientId") long patientId,
            @FormParam("appointmentDate") String appointmentDate,
            @FormParam("statusId") int statusId) throws SQLException{
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");        
        java.sql.Timestamp sqlDate = null;
        try {
            Date formattedDate = sdf.parse(appointmentDate);
            sqlDate = new java.sql.Timestamp(formattedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataUtil dataUtil = new DataUtil();
        Connection con = null;
        Statement stm = null;
        try {
            con = dataUtil.getDataConnection();
            stm = con.createStatement();
            String sqlQuery = "insert into channeling(doctor_id,patient_id,appointment_date,status_id) "
                    + "values (?,?,?,?)";
            PreparedStatement preparedStmt = con.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, doctorId);
            preparedStmt.setLong(2, patientId);
            preparedStmt.setTimestamp(3, sqlDate);
            preparedStmt.setInt(4, statusId);
            
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }       
        
        return Response.status(Response.Status.CREATED).entity("Channeling "
                + "created on :"+appointmentDate).build();
    }
    
    //http://localhost:8080/MediChanneling/rest/channeling/updatedate/6/2015-08-12%2007:30
    @PUT
    @Path("/updatedate/{id}/{appointmentDate}")
    public Response setChannelingDate(
            @PathParam("id") long id, 
            @PathParam("appointmentDate") String appointmentDate)throws SQLException{
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");        
        java.sql.Timestamp sqlDate = null;
        try {
            Date formattedDate = sdf.parse(appointmentDate);
            sqlDate = new java.sql.Timestamp(formattedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataUtil dataUtil = new DataUtil();
        Connection con = null;
        Statement stm = null;
        try {
            con = dataUtil.getDataConnection();
            stm = con.createStatement();
            String sqlQuery = "update channeling set appointment_date=? where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sqlQuery);
            preparedStmt.setTimestamp(1, sqlDate);
            preparedStmt.setLong(2, id);
            
            preparedStmt.execute();
            
        }catch (SQLException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return Response.status(200).entity("Successfully update the channeling with id "+id).build();
    
    }
    
    
    @PUT
    @Path("/updatestatus/{id}/{status}")
    public Response setChannelingStatus(
            @PathParam("id") long id, 
            @PathParam("status") int statusId)throws SQLException{       
        
        DataUtil dataUtil = new DataUtil();
        Connection con = null;
        Statement stm = null;
        try {
            con = dataUtil.getDataConnection();
            stm = con.createStatement();
            String sqlQuery = "update channeling set status_id=? where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sqlQuery);
            preparedStmt.setInt(1, statusId);
            preparedStmt.setLong(2, id);
            
            preparedStmt.execute();
            
        }catch (SQLException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return Response.status(200).entity("Successfully update the channeling with id "+id).build();
    
    }
    
    @DELETE
    @Path("/remove/{id}")
    public Response deeteChanneling(@PathParam("id") long id) throws SQLException{
        
        DataUtil dataUtil = new DataUtil();
        Connection con = null;
        Statement stm = null;
        try {
            con = dataUtil.getDataConnection();
            stm = con.createStatement();
            String sqlQuery = "delete from channeling where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sqlQuery);
            preparedStmt.setLong(1, id);           
            
            preparedStmt.execute();
            
        }catch (SQLException ex) {
            Logger.getLogger(ChannelingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return Response.status(200).entity("Successfully update the channeling with id "+id).build();
        
    
    }
    
}
