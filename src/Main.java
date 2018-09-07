import java.io.*;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args){
		try{
		LinkedList<String> fileList = new LinkedList<String>();
		File file = new File(System.getProperty("user.dir") +"\\Auctiontools-Core.lua");
		System.out.println(file.toString());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Boolean lineFound = false;
		Boolean patched = false;
		String line = reader.readLine();
		
		if (line == null) System.out.println("File not found!");
			
		
		while (line != null) {
			fileList.addLast(line);
			if(line.equals("		elseif stack_count*stack_size>Appraiser.GetTrueItemCount(Appraiser.ActiveSellingItem.itemid,Appraiser.ActiveSellingItem.link) then")){
				System.out.println("Line found at row " +fileList.size() +"!");
				lineFound = true;
			}
			if(!line.equals("			Appraiser:SetMaxStackSize()") && lineFound && !patched){
				fileList.addLast("			Appraiser:SetMaxStackSize()");
				patched = true;
			}
			line = reader.readLine();
		}
		
		reader.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		for (int i = 0; i < fileList.size(); i++){
			writer.write(fileList.get(i));
			writer.newLine();
		}
		
		writer.close();
		System.out.println("Done patching Auctiontools-Core.lua");
		
		}catch (IOException e){
			System.out.println("Error patching Auctiontools-Core.lua: " +e);
		}
	}
}
