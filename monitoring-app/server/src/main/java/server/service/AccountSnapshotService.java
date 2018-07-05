package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import server.data.AccountSnapshotData;
import server.data.AlertData;
import server.domain.AccountSnapshot;
import server.repository.AccountSnapshotRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

@Component
public class AccountSnapshotService {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private HttpEntity<String> entity;

    @Value("${np.url}")
    private String npUrl;

    @Value("${sx.url}")
    private String sxUrl;

    @Autowired
    private AccountSnapshotRepository accountSnapshotRepository;

    public AccountSnapshotService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        entity = new HttpEntity<String>("parameters", headers);
    }

    public NPInformation getNPInformation() {
        return restTemplate.exchange(npUrl, HttpMethod.GET, entity, NPInformation.class).getBody();
    }

    public SXInformation getSXInformation() {
        return restTemplate.exchange(sxUrl, HttpMethod.GET, entity, SXInformation.class).getBody();
    }


    public Collection<AccountSnapshotData> list() {
        final Collection<AccountSnapshotData> accountSnapshots = new LinkedList<>();
        accountSnapshotRepository.getAllByDateAfterOrderByDateDesc(
                Timestamp.valueOf(LocalDateTime.now().minusDays(30))
        ).forEach((account -> accountSnapshots.add(AccountSnapshotData.from(account))));
        return accountSnapshots;
    }

    @Scheduled(cron = "0 0 0,12 * * *")
    private void log() {
        AccountSnapshot accountSnapshot = new AccountSnapshot(java.sql.Date.from(Instant.now()), getNPInformation().getAmount(), getSXInformation().getAmount());
        accountSnapshotRepository.save(accountSnapshot);
    }
}

class NPInformation {
    private String status;
    private double data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public double getAmount() {
        return data;
    }

    @Override
    public String toString() {
        return "NPInformation{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", amount=" + getAmount() +
                '}';
    }
}

class SXInformation {
    private long hash;
    private String identifier;
    private long lastHash;
    private long totalHashes;
    private long validShares;
    private long invalidShares;
    private long expiry;
    private long amtPaid;
    private long amtDue;
    private long txnCount;

    public long getHash() {
        return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getLastHash() {
        return lastHash;
    }

    public void setLastHash(long lastHash) {
        this.lastHash = lastHash;
    }

    public long getTotalHashes() {
        return totalHashes;
    }

    public void setTotalHashes(long totalHashes) {
        this.totalHashes = totalHashes;
    }

    public long getValidShares() {
        return validShares;
    }

    public void setValidShares(long validShares) {
        this.validShares = validShares;
    }

    public long getInvalidShares() {
        return invalidShares;
    }

    public void setInvalidShares(long invalidShares) {
        this.invalidShares = invalidShares;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public long getAmtPaid() {
        return amtPaid;
    }

    public void setAmtPaid(long amtPaid) {
        this.amtPaid = amtPaid;
    }

    public long getAmtDue() {
        return amtDue;
    }

    public void setAmtDue(long amtDue) {
        this.amtDue = amtDue;
    }

    public long getTxnCount() {
        return txnCount;
    }

    public void setTxnCount(long txnCount) {
        this.txnCount = txnCount;
    }

    public double getAmount() {
        return amtDue / 1000000000000d;
    }

    @Override
    public String toString() {
        return "SXInformation{" +
                "hash=" + hash +
                ", identifier='" + identifier + '\'' +
                ", lastHash=" + lastHash +
                ", totalHashes=" + totalHashes +
                ", validShares=" + validShares +
                ", invalidShares=" + invalidShares +
                ", expiry=" + expiry +
                ", amtPaid=" + amtPaid +
                ", amtDue=" + amtDue +
                ", txnCount=" + txnCount +
                ", amount=" + getAmount() +
                '}';
    }
}