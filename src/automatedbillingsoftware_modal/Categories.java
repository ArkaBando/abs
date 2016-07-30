package automatedbillingsoftware_modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Arka
 */
@Entity
@Table(name = "categories_tbl")
public class Categories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int catid;

    @Column(unique = true)
    private String catName;

    private Date catModifiedDate;

    private String catDesc;

    private int status;

    private double discount;

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Date getCatModifiedDate() {
        return catModifiedDate;
    }

    public void setCatModifiedDate(Date catModifiedDate) {
        this.catModifiedDate = catModifiedDate;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
