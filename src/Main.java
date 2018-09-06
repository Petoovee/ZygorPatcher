import java.io.*;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args){
		try{
		LinkedList<String> fileList = new LinkedList<String>();
		File file = new File("Auctiontools-Core.lua");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		FileWriter writer = new FileWriter("Auctiontools-Core.lua");
		Boolean lineFound = false;
		String line = reader.readLine();
		
		if (line == null) System.out.println("File not found!");
			
		
		while (line != null) {
			if(line.equals("		elseif stack_count*stack_size>Appraiser.GetTrueItemCount(Appraiser.ActiveSellingItem.itemid,Appraiser.ActiveSellingItem.link) then")){
				System.out.println("Line found!");
				lineFound = true;
			}
			if(!line.equals("			Appraiser:SetMaxStackSize") && lineFound){
				fileList.addLast("			Appraiser:SetMaxStackSize");
			}
			line = reader.readLine();
			fileList.addLast(line);
			System.out.println("DEBUG: " +line);
		}
		
		reader.close();
		
		for (int i = 0; i < fileList.size(); i++){
			writer.append(fileList.get(i));
		}
		writer.close();
		System.out.println("Done patching Auctiontools-Core.lua");
		
		}catch (IOException e){
			System.out.println("Error patching Auctiontools-Core.lua: " +e);
		}
	}
}
