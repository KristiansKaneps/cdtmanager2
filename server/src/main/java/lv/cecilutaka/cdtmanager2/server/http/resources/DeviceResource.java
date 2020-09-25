package lv.cecilutaka.cdtmanager2.server.http.resources;

import io.netty.handler.codec.http.HttpResponseStatus;
import lv.cecilutaka.cdtmanager2.api.common.device.IDevice;
import lv.cecilutaka.cdtmanager2.api.common.registry.RegistryValue;
import lv.cecilutaka.cdtmanager2.common.device.bridge.BridgeImpl;
import lv.cecilutaka.cdtmanager2.common.log.Log;
import lv.cecilutaka.cdtmanager2.server.Server;
import lv.cecilutaka.cdtmanager2.server.http.WebApplication;
import lv.cecilutaka.cdtmanager2.server.http.resources.json.*;
import lv.cecilutaka.cdtmanager2.server.registry.DeviceReadOnlyRegistry;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.response.RawResponseWrapper;
import org.restexpress.response.ResponseProcessor;
import org.restexpress.serialization.SerializationSettings;
import org.restexpress.serialization.json.JacksonJsonProcessor;
import org.restexpress.serialization.xml.XstreamXmlProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeviceResource
{
	private static final JacksonJsonProcessor jsonProcessor = new JacksonJsonProcessor();
	private static final XstreamXmlProcessor xmlProcessor = new XstreamXmlProcessor();

	static
	{
		xmlProcessor.alias("firmware", JsonFirmware.class);

		xmlProcessor.alias("relay", JsonRelay.class);
		xmlProcessor.alias("bridge", JsonBridge.class);
		xmlProcessor.alias("mono-floodlight", JsonMonoFloodlight.class);
		xmlProcessor.alias("rgb-floodlight", JsonRGBFloodlight.class);
		xmlProcessor.alias("rgb-matrix", JsonRGBMatrix.class);
	}

	private static final SerializationSettings jsonSerializationSettings = new SerializationSettings(
			"application/json",
			new ResponseProcessor(jsonProcessor, new RawResponseWrapper())
	);
	private static final SerializationSettings xmlSerializationSettings = new SerializationSettings(
			"application/xml",
			new ResponseProcessor(xmlProcessor, new RawResponseWrapper())
	);

	protected final Supplier<SerializationSettings> serialization;

	protected final WebApplication app;

	public DeviceResource(WebApplication app)
	{
		this.app = app;
		serialization = app.getResponseType() == WebApplication.ResponseType.JSON ? () -> jsonSerializationSettings : () -> xmlSerializationSettings;
	}

	/**
	 * RestExpress automatically calls this method.
	 */
	public void read(Request request, Response response)
	{
		Log.d("REST", "Request from " + request.getRemoteAddress() + ": [" + request.getHttpMethod() + "]" + request.getPath());
		Server s = app.getServerInstance();

		response.setSerializationSettings(serialization.get());
		response.setIsSerialized(true);
		response.setResponseStatus(HttpResponseStatus.OK);

		response.setBody(getDevices(s));
	}

	public List<? extends JsonDevice> getDevices(Server server)
	{
		DeviceReadOnlyRegistry registry = server.getDeviceReadOnlyRegistry();
		Collection<RegistryValue<IDevice>> values = registry.getValueCollection();

		List<JsonDevice> devices = new ArrayList<>();
		for(RegistryValue<IDevice> value : values)
			if(!value.isEmpty()) devices.add(new JsonDevice(value.get()));

		return devices;
	}

	/**
	 * RestExpress automatically calls this method.
	 */
	public void create(Request request, Response response)
	{
		response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
		response.setIsSerialized(false);
	}
}
