package lv.cecilutaka.cdtmanager2.server.mqtt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConsumeMqttMessage
{
	/**
	 * @return subscription ID which this class consumes
	 */
	int subscriptionId();
}
