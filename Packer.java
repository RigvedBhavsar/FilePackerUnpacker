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
import java.security.MessageDigest;
import java.io.*;
import java.util.*;

public class Packer
{
	FileOutputStream outstream=null;
	String ValidExt[]={".txt",".c",".java",".cpp",".h",".php"};
	
	Hashtable<String,String> ht=new Hashtable<>();

	//src contain folder name and Dest contain packing file name
	public Packer(String src,String Dest)throws Exception
	{
		outstream =new FileOutputStream(Dest);

		File folder=new File(src);
		if(folder.exists())
		{	int i=0;
			String arr[]=folder.list();
			File f1=new File(arr[i]);
			if(f1.isDirectory())
				listAllFiles(arr[i]);
			else
				listAllFiles(src);		
		}
		

	} 
	public static byte[] createChecksum(String filename) throws Exception 
	{
		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do 
		{
			numRead = fis.read(buffer);
			if (numRead > 0) 
			{
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public String getMD5Checksum(String filename) throws Exception 
	{
		//String name2=file.getFileName().toString();
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i=0; i < b.length; i++) 
		{
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;

	} 
	public void listAllFiles(String path) //path contains folder name itself
	{
		try
			(Stream<Path>  paths=Files.walk(Paths.get(path)))
			{
				paths.forEach(filePath->
						{
						if(Files.isRegularFile(filePath))
						{
						try
						{
						String name=filePath.getFileName().toString();

						File file=new File(filePath.toString());

						//get the checksum of file in key variable	
						String key=getMD5Checksum(filePath.toString());

						if(ht.containsValue(key))	
						{
						}
						else	
						{
						ht.put(name,key);
						}

						Enumeration e=ht.keys();

						int i=0;
						List<String> ll2=Collections.list(e);
						String ext=name.substring(name.lastIndexOf("."));

						List<String> list=Arrays.asList(ValidExt);

						if(list.contains(ext))
						{

							if(ll2.contains(name))
							{
								Pack(file.getAbsolutePath());
							}

						}
						}
						catch(Exception e)
						{
							System.out.println("Inner catch");
							e.printStackTrace();
						}
						}
						});


			}
		catch(IOException e)
		{
			System.out.println("HELLO SS");
		}
	}
	public void Pack(String filePath)
	{

		FileInputStream instream=null;
		try
		{

			byte[] buffer=new byte[1024];
			int length;
			byte temp[]=new byte[100];

			File fobj=new File(filePath);

			String name1=fobj.getName();

			String Header=fobj.getName()+" "+fobj.length();

			for(int i=Header.length();i<100;i++)
			{
				Header+=" ";
			}

			temp=Header.getBytes();
			instream=new FileInputStream(filePath);
			outstream.write(temp,0,temp.length);

			while((length=instream.read(buffer))>0)
			{
				outstream.write(buffer,0,length);
			}

			instream.close();

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}






