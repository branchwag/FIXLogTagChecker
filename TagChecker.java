import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class TagChecker {

    //map to store tag count per msgtype
    private Map<String, Map<String, Integer>> messageTypeStats = new HashMap<>();

    public void analyzeLogs(String filename) {
	File file = new File(filename);
	if (!file.exists()) {
	     System.err.println("Error: File " + filename + " does not exists");
	    return;
	}
	System.out.println("Processing file: " + filename);
	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	    String line;
	    int lineCount = 0;
	    while ((line = reader.readLine()) != null) {
		lineCount++;
		try {
		    analyzeFIXMessage(line);
		} catch (Exception e) {
		    System.err.println("Warning: Error processing line " + lineCount + ": " + e.getMessage());
		}
	    }
	} catch (FileNotFoundException e) {
	    System.err.println("Error: File " + filename + " not found.");
	} catch (IOException e) {
	    System.err.println("Error: an IO exception occurred while reading file.");
	}
    }

    public void analyzeFIXMessage(String message) {
		String[] fields = message.split("\\|");
		String messageType = null;

		for (String field : fields) {
			String[] tagValue = field.split("=");
			//might need to change this from 2 later
			if (tagValue.length != 2) continue;
			String tag = tagValue[0];

			if (tag.equals("35")) {
				messageType = tagValue[1];
				messageTypeStats.putIfAbsent(messageType, new HashMap<>());
			}

			//get count of tag for this msgtype
			if (messageType != null) {
				Map<String, Integer> tagCounts = messageTypeStats.get(messageType);
				tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
			}
		}
	}


	public void printStats() {
		List<String> sortedMsgTypes = new ArrayList<>(messageTypeStats.keySet());
		Collections.sort(sortedMsgTypes);

		for (String msgType : sortedMsgTypes) {
		    System.out.println("\nMessage Type: " + msgType + " - " + getMessageTypeName(msgType));
		    System.out.println("Tags appearing in this message type:");
		    
		    Map<String, Integer> tagCounts = messageTypeStats.get(msgType);
		    List<Map.Entry<String, Integer>> sortedTags = new ArrayList<>(tagCounts.entrySet());
		    sortedTags.sort(Map.Entry.comparingByKey());
		    
		    for (Map.Entry<String, Integer> entry : sortedTags) {
			System.out.printf("Tag %s (%s): %d occurrences%n", 
			    entry.getKey(), 
			    getTagName(entry.getKey()), 
			    entry.getValue());
		    }
		}
	}



    //can add more msgtypes here but for now just doing basic ones
    private String getMessageTypeName(String type) {
        switch (type) {
            case "0": return "Heartbeat";
            case "A": return "Logon";
            case "8": return "Execution Report";
            default: return "Unknown";
        }
    }
    
    //doing names of common tags for now for testing, but in future need to revise the below to read off appropriate xml dictionary based odd of FIX version detected in logs
    private String getTagName(String tag) {
        switch (tag) {
            case "8": return "BeginString";
            case "9": return "BodyLength";
            case "10": return "CheckSum";
            case "11": return "ClOrdID";
            case "17": return "ExecID";
            case "31": return "LastPx";
            case "32": return "LastQty";
            case "34": return "MsgSeqNum";
            case "35": return "MsgType";
            case "38": return "OrderQty";
            case "39": return "OrdStatus";
            case "44": return "Price";
            case "49": return "SenderCompID";
            case "52": return "SendingTime";
            case "54": return "Side";
            case "55": return "Symbol";
            case "56": return "TargetCompID";
            case "98": return "EncryptMethod";
            case "108": return "HeartBtInt";
            case "150": return "ExecType";
            default: return "Unknown";
        }
    }
	public static void main (String[] args) {
		
		if (args.length != 1) {
		    System.out.println("Usage: java TagChecker <filename>");
		    System.out.println("Example: java TagChecker TESTSESSION.message.log");
		    return;
		}


		TagChecker tagChecker = new TagChecker();
		tagChecker.analyzeLogs(args[0]);
		System.out.println("Processing...");
		tagChecker.printStats();
	}
}
