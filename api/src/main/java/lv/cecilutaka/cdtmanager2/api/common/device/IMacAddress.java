package lv.cecilutaka.cdtmanager2.api.common.device;

public interface IMacAddress
{
	void setFromString(String str) throws IllegalArgumentException;

	void setOctets(byte[] octets);

	byte[] getOctets();

	byte getOctet(int octetIndex);

	@Override
	String toString();

	@Override
	boolean equals(Object object);
}
