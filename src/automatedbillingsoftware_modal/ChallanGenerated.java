/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedbillingsoftware_modal;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Arka
 */
@Entity
@Table(name = "ChallGen_tbl")
public class ChallanGenerated {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Challan> challanList;

    private String challanNo;

    private Date deliveryDate;

    private Date date;

    private String challanFor;

    private String vatNo;

    private String cSTNo;

    private String companyName;

    private String phone;

    private String address;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Challan> getChallanList() {
        return challanList;
    }

    public void setChallanList(List<Challan> challanList) {
        this.challanList = challanList;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String ChallanNo) {
        this.challanNo = ChallanNo;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChallanFor() {
        return challanFor;
    }

    public void setChallanFor(String challanFor) {
        this.challanFor = challanFor;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getCSTNo() {
        return cSTNo;
    }

    public void setCSTNo(String cSTNo) {
        this.cSTNo = cSTNo;
    }

    public String getcSTNo() {
        return cSTNo;
    }

    public void setcSTNo(String cSTNo) {
        this.cSTNo = cSTNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
