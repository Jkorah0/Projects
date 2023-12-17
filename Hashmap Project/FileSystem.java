
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    // TODO
    public FileSystem() {
        nameMap = new MyHashMap<>();
        dateMap = new MyHashMap<>();}

    // TODO
    public FileSystem(String inputFile) {
        nameMap = new MyHashMap<>();
        dateMap = new MyHashMap<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] s = sc.nextLine().split(", ");
                // Add your code here
                add(s[0], s[1], s[2]);}
            sc.close();}
        catch (FileNotFoundException e) { System.out.println(e);}}

    // TODO
    public boolean add(String fileName, String directory, String modifiedDate) {
        if(fileName.equals(null)) {fileName = "";}
        if(directory.equals(null)) {directory = "/";}
        if(modifiedDate.equals(null)) {modifiedDate = "01/01/2021";}
        ArrayList<FileData> names = new ArrayList<>();
		ArrayList<FileData> dates = new ArrayList<>();
        FileData fd = new FileData(fileName, directory, modifiedDate);

        if ((findFile(fileName, directory) != null)) {return false;}
        
        else {
            names = findFilesByName(fileName);
            names.add(fd);
            nameMap.set(fileName, names);
            dates = findFilesByName2(modifiedDate);
            dates.add(fd);
            dateMap.set(modifiedDate, dates);
             return true;}}

    // TODO
    public FileData findFile(String name, String directory) {
        if(nameMap.containsKey(name)){
            ArrayList<FileData> a = nameMap.get(name);

            for(int i = 0; i < a.size(); i++){
                if(directory.equals(a.get(i).dir) && name.equals(a.get(i).name)){return a.get(i);}}
            return null;}
        else{return null;}}

    // TODO
    public ArrayList<String> findAllFilesName() {
        ArrayList<String> al = new ArrayList<>();
        if(nameMap.isEmpty()){return al;}
        else{
            for(int i = 0; i < nameMap.size(); i++){
                String s = nameMap.keys().get(i);
                al.add(s);}
            return al;}}

    // TODO
    public ArrayList<FileData> findFilesByName(String name) {
        ArrayList<FileData> al = new ArrayList<>();
        if(nameMap.get(name) == null){return al;}
        else{
            for(FileData item : nameMap.get(name)){al.add(item);}
            return al;}}
    

    public ArrayList<FileData> findFilesByName2(String name) {
    	ArrayList<FileData> filename = new ArrayList<>();
        if(dateMap.get(name) == null){
        	return filename;
        }
        else{
            for(FileData names : dateMap.get(name)){
            	filename.add(names);
            }
            return filename;
       }
    }


    // TODO
    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
        ArrayList<FileData> al = new ArrayList<>();
        if(dateMap.get(modifiedDate) == null){return al;}
        else{ for(FileData item : dateMap.get(modifiedDate)){
                al.add(item);}
            return al;}}

    // TODO
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
        ArrayList<FileData> item = dateMap.get(modifiedDate);
    	ArrayList<FileData> al = new ArrayList<>();
    	for (int i = 0; i < item.size(); i++) {
            FileData t = item.get(i);
            int num = 0;
            for (int j = i; j < item.size(); j++) {
                if (item.get(j).name.equals(t.name)) {num++;}}
            if (num >= 2)  {al.add(t);}}
        for (int i = al.size() - 1; i <= 0; i--) {
            FileData t = al.get(i);
            boolean tr = true;
            for (int j = 0; j < al.size(); j++) {
                if (t.name.equals(al.get(j).name) && !(t.dir.equals(al.get(j).dir))) {tr = false;}}
            if (tr == true) {al.remove(i);}}
        return al;}
    
    // TODO
   public boolean removeByName(String name) {
   	 boolean del = false;

   	    if (nameMap.containsKey(name)) {
   	        ArrayList<FileData> al = nameMap.get(name);
   	        for (int i = al.size() - 1; i >= 0; i--) {
   	            FileData fd = al.get(i);
   	            if (name.equals(fd.name)) {
   	                al.remove(i);
   	                if (al.isEmpty()) {
   	                    nameMap.remove(name);}
   	             del = true;}}}
   	    if (dateMap.containsKey(name)) {
   	        ArrayList<FileData> al = dateMap.get(name);
   	        for (int i = al.size() - 1; i >= 0; i--) {
   	            FileData fd = al.get(i);
   	            if (name.equals(fd.name)) {al.remove(i);
   	                if (al.isEmpty()) {
   	                    dateMap.remove(name);}
   	             del = true;}}}
   	    return del;}
    // TODO
    public boolean removeFile(String name, String directory) {
        if (nameMap.containsKey(name)) {
            ArrayList<FileData> al = nameMap.get(name);
            for (int i = 0; i < al.size(); i++) {
                if (directory.equals(al.get(i).dir) && name.equals(al.get(i).name)) {
                    al.remove(i);
                    return true;}}}
        else if (dateMap.containsKey(name)) {
        	ArrayList<FileData> als = dateMap.get(name);
            for (int i = 0; i < als.size(); i++) {
            	if (directory.equals(als.get(i).dir) && name.equals(als.get(i).name)) {
            		als.remove(i);
                    return true;}}}
        return false;}}
