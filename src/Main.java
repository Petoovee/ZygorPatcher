import java.io.*;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) {
		FixStackSize();
		FixRowCount();
		FixIrrelevantResults();
	}
	
	public static void FixIrrelevantResults() {
		try {
			LinkedList<String> fileList = new LinkedList<String>();
			File file = new File(System.getProperty("user.dir") + "\\Auctiontools-Buy.lua");
			System.out.println(file.toString());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			
			if (line == null)
				System.out.println(file.toString() + " not found!");
			
			while (line != null) {
				fileList.addLast(line);
				if (line.equals("	elseif status==\"noresults\" then")) {
					System.out.println("Criteria found at row " + fileList.size() + "!");
					line = reader.readLine();
					if (line.equals("		text = \"No results\"")) {
						fileList.addLast("		for i,v in pairs(ZGV.db.char.GGbuyitems) do");
						fileList.addLast("			if v.itemid == invItem.itemid then");
						fileList.addLast("				table.remove(ZGV.db.char.GGbuyitems,i)");
						fileList.addLast("			end");
						fileList.addLast("		end");
						System.out.println("Injecting FixIrrelevantResults part 1 patch");
					} else if(line.equals("		for i,v in pairs(ZGV.db.char.GGbuyitems) do")) {
						System.out.println("FixIrrelevantResults part 1 already patched");
					} else {
						System.out.println("Unknown error");
					}
					fileList.addLast(line);
				}
				if (line.equals("	elseif status==\"nodeals\" then")) {
					System.out.println("Criteria found at row " + fileList.size() + "!");
					line = reader.readLine();
					if (line.equals("		text = \"No deals\"")) {
						fileList.addLast("		for i,v in pairs(ZGV.db.char.GGbuyitems) do");
						fileList.addLast("			if v.itemid == invItem.itemid then");
						fileList.addLast("				table.remove(ZGV.db.char.GGbuyitems,i)");
						fileList.addLast("			end");
						fileList.addLast("		end");
						System.out.println("Injecting FixIrrelevantResults part 2 patch");
					} else if(line.equals("		for i,v in pairs(ZGV.db.char.GGbuyitems) do")) {
						System.out.println("FixIrrelevantResults part 2 already patched");
					} else {
						System.out.println("Unknown error");
					}
					fileList.addLast(line);
				}
				line = reader.readLine();
			}
			
			reader.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(fileList.get(0));
			for (int i = 1; i < fileList.size(); i++) {
				writer.newLine();
				writer.write(fileList.get(i));
			}

			writer.close();
			System.out.println("Done patching Auctiontools-Core.lua");
			
		} catch (IOException e) {
			System.out.println("Error patching Auctiontools-Buy.lua: " + e);
		}
		
		
		/* Lua code to interpret and inject
		 * Taken from -Buy.lua@728
		 * To be injected into -Buy.lua@297
		 * 	
	for i,v in pairs(ZGV.db.char.GGbuyitems) do
		if v.itemid == invItem.itemid then
			table.remove(ZGV.db.char.GGbuyitems,i)
		end
	end
		 */
	}

	public static void FixRowCount() {
		try {
			LinkedList<String> fileList = new LinkedList<String>();
			File file = new File(System.getProperty("user.dir") + "\\Auctiontools-View.lua");
			System.out.println(file.toString());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();

			if (line == null)
				System.out.println(file.toString() + " not found!");

			while (line != null) {
				fileList.addLast(line);
				if (line.equals("local SELL_INVENTORY_DATA = {")) {
					System.out.println("Criteria found at row " + fileList.size() + "!");
					line = reader.readLine();
					if (line.equals("	ROW_COUNT = 14,")) {
						fileList.addLast("	ROW_COUNT = 24,");
						System.out.println("Injecting FixRowCount patch");
					} else if(line.equals("	ROW_COUNT = 24,")) {
						System.out.println("FixRowCount already patched");
						fileList.addLast(line);
					} else {
						System.out.println("Unknown error");
					}
				}
				line = reader.readLine();
			}

			reader.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(fileList.get(0));
			for (int i = 1; i < fileList.size(); i++) {
				writer.newLine();
				writer.write(fileList.get(i));
			}

			writer.close();
			System.out.println("Done patching Auctiontools-Core.lua");

		} catch (IOException e) {
			System.out.println("Error patching Auctiontools-Core.lua: " + e);
		}
	}

	public static void FixStackSize() {
		try {
			LinkedList<String> fileList = new LinkedList<String>();
			File file = new File(System.getProperty("user.dir") + "\\Auctiontools-Core.lua");
			System.out.println(file.toString());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();

			if (line == null)
				System.out.println(file.toString() + " not found!");

			while (line != null) {
				fileList.addLast(line);
				if (line.equals(
						"		elseif stack_count*stack_size>Appraiser.GetTrueItemCount(Appraiser.ActiveSellingItem.itemid,Appraiser.ActiveSellingItem.link) then")) {
					System.out.println("Criteria found at row " + fileList.size() + "!");
					line = reader.readLine();
					if (!line.equals("			Appraiser:SetMaxStackCount()")) {
						fileList.addLast("			Appraiser:SetMaxStackCount()");
						fileList.addLast("			Appraiser:SetMaxStackSize()");
						System.out.println("Injecting FixStackSize patch");
					} else {
						System.out.println("FixStackSize already patched");
					}
					fileList.addLast(line);
				}
				line = reader.readLine();
			}

			reader.close();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(fileList.get(0));
			for (int i = 1; i < fileList.size(); i++) {
				writer.newLine();
				writer.write(fileList.get(i));
			}

			writer.close();
			System.out.println("Done patching Auctiontools-Core.lua");

		} catch (IOException e) {
			System.out.println("Error patching Auctiontools-Core.lua: " + e);
		}
	}
}
