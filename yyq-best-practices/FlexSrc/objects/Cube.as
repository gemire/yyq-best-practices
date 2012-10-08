package objects
{
	import org.papervision3d.materials.utils.MaterialsList;
	import org.papervision3d.objects.primitives.Cube;
	
	public class Cube extends org.papervision3d.objects.primitives.Cube
	{
		public function Cube(materials:MaterialsList, width:Number=100, depth:Number=100, height:Number=100, segmentsS:int=1, segmentsT:int=1, segmentsH:int=1, insideFaces:int=0, excludeFaces:int=0)
		{
			super(materials, width, depth, height, segmentsS, segmentsT, segmentsH, insideFaces, excludeFaces);
		}
		public var color:int;
	}
}