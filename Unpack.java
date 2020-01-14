import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Unpack
{
	FileOutputStream outstream=null,out=null;

	public Unpack(String src,String dest)throws Exception
	{
		unpack(src,dest);
	}
	public void unpack(String filePath,String dest) throws Exception
	{
		try
		{
			System.out.println("UNPACK FILE PATH :"+filePath);
			File f=new File(dest);
			f.mkdir();
			if(f.exists())
			{	
				FileInputStream is=new FileInputStream(filePath);


				int length=0,i=0,j=0,len=0;

				byte header[]=new byte[100];
				byte buff[]=new byte[1024];

				while((length=is.read(header,0,100))>0)
				{	
					String s=new String(header);
					String str[]=s.split("\\s");;
					String name=str[0];
					len=Integer.parseInt(str[1]);
					byte arr[]=new byte[len];
					File fp=new File(dest,name);
					FileOutputStream os=new FileOutputStream(fp);		
					is.read(buff,0,len);
					os.write(buff,0,len);
					os.close();
				}
			}
			
		}
		catch(Exception e)
		{			
			System.out.println(e);
		}
	}
}



