package lv.cecilutaka.cdtmanager2.common.device;

import lv.cecilutaka.cdtmanager2.api.common.device.IMacAddress;

public class MacAddress implements IMacAddress
{
	private final byte[] octets = new byte[6];

	public MacAddress() { }

	public MacAddress(byte[] octets)
	{
		setOctets(octets);
	}

	public MacAddress(String str) throws IllegalArgumentException
	{
		setFromString(str);
	}

	@Override
	public void setFromString(String str) throws IllegalArgumentException
	{
		try
		{
			int s1 = str.indexOf(":") - 2;
			int e1 = s1 + 2;

			int s2 = e1 + 1;
			int e2 = s2 + 2;

			int s3 = e2 + 1;
			int e3 = s3 + 2;

			int s4 = e3 + 1;
			int e4 = s4 + 2;

			int s5 = e4 + 1;
			int e5 = s5 + 2;

			int s6 = e5 + 1;
			int e6 = s6 + 2;

			octets[0] = (byte) Integer.parseInt(str.substring(s1, e1), 16);
			octets[1] = (byte) Integer.parseInt(str.substring(s2, e2), 16);
			octets[2] = (byte) Integer.parseInt(str.substring(s3, e3), 16);
			octets[3] = (byte) Integer.parseInt(str.substring(s4, e4), 16);
			octets[4] = (byte) Integer.parseInt(str.substring(s5, e5), 16);
			octets[5] = (byte) Integer.parseInt(str.substring(s6, e6), 16);
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public void setOctets(byte[] octets)
	{
		this.octets[0] = octets[0];
		this.octets[1] = octets[1];
		this.octets[2] = octets[2];
		this.octets[3] = octets[3];
		this.octets[4] = octets[4];
		this.octets[5] = octets[5];
	}

	@Override
	public byte[] getOctets()
	{
		return octets;
	}

	@Override
	public byte getOctet(int octetIndex)
	{
		if(octetIndex < 0 || octetIndex > 5) return 0;
		return octets[octetIndex];
	}

	@Override
	public String toString()
	{
		return Integer.toHexString(octets[0]) + ':'
				+ Integer.toHexString(octets[1]) + ':'
				+ Integer.toHexString(octets[2]) + ':'
				+ Integer.toHexString(octets[3]) + ':'
				+ Integer.toHexString(octets[4]) + ':'
				+ Integer.toHexString(octets[5]);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(super.equals(obj)) return true;
		if(obj instanceof IMacAddress)
		{
			IMacAddress mac = (IMacAddress) obj;
			byte[] oct = mac.getOctets();

			if(oct == null || oct.length != octets.length)
				return false;

			return oct[0] == octets[0]
					&& oct[1] == octets[1]
					&& oct[2] == octets[2]
					&& oct[3] == octets[3]
					&& oct[4] == octets[4]
					&& oct[5] == octets[5];
		}
		return false;
	}
}
