package geekbrains.home.des.springleveltwo.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@ImportResource("classpath:/integration/http-purchase-integration.xml")
public class IntegrationPurchaseConfig {

    private DirectChannel PurchaseChannel;

    public IntegrationPurchaseConfig(@Qualifier("purchaseChannel") DirectChannel purchaseChannel) {
        PurchaseChannel = purchaseChannel;
    }

    public DirectChannel getPurchaseChannel() {
        return PurchaseChannel;
    }
}
