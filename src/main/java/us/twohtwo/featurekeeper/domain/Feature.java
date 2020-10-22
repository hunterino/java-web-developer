package us.twohtwo.featurekeeper.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import us.twohtwo.featurekeeper.domain.enumeration.Client;

import us.twohtwo.featurekeeper.domain.enumeration.ProductArea;

/**
 * A Feature.
 */
@Entity
@Table(name = "feature",uniqueConstraints={
    @UniqueConstraint(columnNames = {"client", "client_priority"})
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "client")
    private Client client;

    @Column(name = "client_priority")
    private Long clientPriority;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_area")
    private ProductArea productArea;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Feature title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Feature description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public Feature client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getClientPriority() {
        return clientPriority;
    }

    public Feature clientPriority(Long clientPriority) {
        this.clientPriority = clientPriority;
        return this;
    }

    public void setClientPriority(Long clientPriority) {
        this.clientPriority = clientPriority;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public Feature targetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
        return this;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public ProductArea getProductArea() {
        return productArea;
    }

    public Feature productArea(ProductArea productArea) {
        this.productArea = productArea;
        return this;
    }

    public void setProductArea(ProductArea productArea) {
        this.productArea = productArea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feature)) {
            return false;
        }
        return id != null && id.equals(((Feature) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feature{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", client='" + getClient() + "'" +
            ", clientPriority=" + getClientPriority() +
            ", targetDate='" + getTargetDate() + "'" +
            ", productArea='" + getProductArea() + "'" +
            "}";
    }
}
