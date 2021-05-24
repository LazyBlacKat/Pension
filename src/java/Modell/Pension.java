/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modell;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONObject;

/**
 *
 * @author Enikő
 */
@Entity
@Table(name = "pension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pension.findAll", query = "SELECT p FROM Pension p")
    , @NamedQuery(name = "Pension.findById", query = "SELECT p FROM Pension p WHERE p.id = :id")
    , @NamedQuery(name = "Pension.findByUserid", query = "SELECT p FROM Pension p WHERE p.userid = :userid")
    , @NamedQuery(name = "Pension.findByName", query = "SELECT p FROM Pension p WHERE p.name = :name")
    , @NamedQuery(name = "Pension.findBySettlement", query = "SELECT p FROM Pension p WHERE p.settlement = :settlement")
    , @NamedQuery(name = "Pension.findByAddress", query = "SELECT p FROM Pension p WHERE p.address = :address")
    , @NamedQuery(name = "Pension.findByWebsite", query = "SELECT p FROM Pension p WHERE p.website = :website")
    , @NamedQuery(name = "Pension.findByPhone", query = "SELECT p FROM Pension p WHERE p.phone = :phone")
    , @NamedQuery(name = "Pension.findByIntro", query = "SELECT p FROM Pension p WHERE p.intro = :intro")
    , @NamedQuery(name = "Pension.findByPicture", query = "SELECT p FROM Pension p WHERE p.picture = :picture")
    , @NamedQuery(name = "Pension.findByStatus", query = "SELECT p FROM Pension p WHERE p.status = :status")})
public class Pension implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userid")
    private int userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "settlement")
    private String settlement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "website")
    private String website;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "intro")
    private String intro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "picture")
    private String picture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;

    public Pension() {
    }

    public Pension(Integer id) {
        this.id = id;
    }

    public Pension(Integer id, int userid, String name, String settlement, String address, String website, String phone, String intro, String picture, int status) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.settlement = settlement;
        this.address = address;
        this.website = website;
        this.phone = phone;
        this.intro = intro;
        this.picture = picture;
        this.status = status;
    }
        public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("id",this.id);
        object.put("userid",this.userid);
        object.put("name",this.name);
        object.put("settlement",this.settlement);
        object.put("address",this.address);
        object.put("website",this.website);
        object.put("phone",this.phone);
        object.put("intro",this.intro);
        object.put("picture",this.picture);
        object.put("status",this.status);
        
        return object;
    }

    public Integer getId() {
        return id;
    }

    public static Pension getPensionById(int id){
    EntityManager em = Database.getDbConn();
    return em.find(Pension.class, id);
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pension)) {
            return false;
        }
        Pension other = (Pension) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modell.Pension[ id=" + id + " ]";
    }
    
}
