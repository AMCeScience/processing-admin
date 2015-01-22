package nl.amc.biolab.admin.output.objects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import dockingadmin.crappy.logger.Logger;

/**
 *
 * @author Allard van Altena
 */
public class EnergyMap {
	private boolean INITIALISED = false;
    private final TreeMap<String, String> ENERGY_MAP = new TreeMap<String, String>();
    private final ArrayList<ArrayList<String>> ENERGY_LIST = new ArrayList<ArrayList<String>>();
    private int COUNT = 0;
    
    public EnergyMap() {}
    
    public void initEnergyMapping(String csvName) {
        Logger.log(csvName, 3);
        
        try {
            BufferedReader csvFile = new BufferedReader(new FileReader(csvName));
            
            String line;
            
            int count = 0;
            
            while((line = csvFile.readLine()) != null) {                
                // Split name from row
                String[] row = line.split(",", 2);
                
                if(row.length > 1) { 
                    // Add row to map
                	if (count < 100) {
                		_addRow(row[0], row[1].split(","));
                		
                		count++;
                	}
                    _addFlotRow(row[0], row[1].split(","));
                    _addLigandCount();
                }
            }
            
            csvFile.close();
        } catch(FileNotFoundException e) {
        	Logger.log(e, 1);
        } catch(IOException ex) {
        	Logger.log(ex, 1);
        }
        
        _setInitialised();
    }
    
    public TreeMap<String, String> getEnergyMap() {
    	return ENERGY_MAP;
    }
    
    public ArrayList<ArrayList<String>> getEnergyListForFlot() {
        return ENERGY_LIST;
    }
    
    public int getLigandCount() {
        return COUNT;
    }
    
    public boolean getInitialised() {
    	return INITIALISED;
    }
    
    private void _setInitialised() {
    	INITIALISED = true;
    }
    
    private void _addLigandCount() {
        COUNT++;
    }
    
    private void _addRow(String name, String[] row) {
        ENERGY_MAP.put(name, row[0]);
    }
    
    private void _addFlotRow(String name, String[] row) {
    	ArrayList<String> tick = new ArrayList<String>();
        
        tick.add(name);
        tick.add(row[0]);
        
        ENERGY_LIST.add(tick);
    }
}