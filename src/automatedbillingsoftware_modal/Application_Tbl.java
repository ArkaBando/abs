
package automatedbillingsoftware_modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Arka
 */
@Entity
@Table(name="app_tbl")
public class Application_Tbl implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "ip")
    private String ipAddress;
    
    @Column(name="mac")
    private String macAddress;
    
    @Column(name="name")
    private String name;
    
    @Column(name="type")
    private String type;
    
    @Column(name="doi")
    @Temporal(TemporalType.DATE)
    private Date dateOfInstallation;
    
    @Column(name="status")
    private int status;
    
    @Column(name="text")
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateOfInstallation() {
        return dateOfInstallation;
    }

    public void setDateOfInstallation(Date dateOfInstallation) {
        this.dateOfInstallation = dateOfInstallation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
