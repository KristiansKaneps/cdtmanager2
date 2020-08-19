package lv.cecilutaka.cdtmanager2.common.device.matrix;

import lv.cecilutaka.cdtmanager2.api.common.device.matrix.IRGBMatrix;
import lv.cecilutaka.cdtmanager2.common.device.floodlight.RGBFloodlightImpl;

public class RGBMatrixImpl extends RGBFloodlightImpl implements IRGBMatrix
{
	public RGBMatrixImpl(int id)
	{
		super(id);
	}

	@Override
	protected String _toStringPart0()
	{
		return "RGBMatrix";
	}
}
