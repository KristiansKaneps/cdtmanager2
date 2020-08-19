package lv.cecilutaka.cdtmanager2.api.common.color;

public class Color
{
	public static final Color BLACK = new Color(0, 0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255, 255);

	public static Color randomColor(boolean randomAlpha)
	{
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (randomAlpha ? Math.random() * 255 : 255));
	}

	public static Color randomColor()
	{
		return randomColor(false);
	}

	protected int r, g, b, a;

	public Color()
	{
		this(0, 0, 0, 255);
	}

	public Color(int rgba)
	{
		setRGBA(rgba);
	}

	public Color(int r, int g, int b)
	{
		this(r, g, b, 255);
	}

	public Color(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 *
	 * @param rgba - ARGB channels respectively
	 */
	public void setRGBA(int rgba)
	{
		a = (rgba >>> 24) & 0xff;
		r = (rgba >>> 16) & 0xff;
		g = (rgba >>> 8) & 0xff;
		b = rgba & 0xff;
	}

	public void setRGBA(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void setRed(int r)
	{
		this.r = r;
	}

	public void setGreen(int g)
	{
		this.g = g;
	}

	public void setBlue(int b)
	{
		this.b = b;
	}

	public void setAlpha(int a)
	{
		this.a = a;
	}

	public void copyFrom(Color color)
	{
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	public int getRGBA()
	{
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	public int getRed()
	{
		return r;
	}

	public int getGreen()
	{
		return g;
	}

	public int getBlue()
	{
		return b;
	}

	public int getAlpha()
	{
		return a;
	}

	public boolean isTransparent()
	{
		return a == 0;
	}

	public boolean isBlack()
	{
		return r == 0 && g == 0 && b == 0;
	}

	public boolean isWhite()
	{
		return r == 0xff && g == 0xff && b == 0xff;
	}

	@Override
	public String toString()
	{
		return "Color(r=" + r + "; g=" + g + "; b=" + b + "; a=" + a + ")";
	}

	@Override
	public boolean equals(Object obj)
	{
		return this == obj || (obj instanceof Color && getRGBA() == ((Color) obj).getRGBA());
	}
}
