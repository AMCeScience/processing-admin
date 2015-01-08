package nl.amc.biolab.admin.output.tools;

import java.io.File;
import java.util.LinkedHashMap;

import nl.amc.biolab.admin.constants.VarConfig;
import nl.amc.biolab.admin.output.objects.EnergyMap;
import nl.amc.biolab.tools.FileCheck;
import nl.amc.biolab.tools.Unzipper;
import dockingadmin.crappy.logger.Logger;

/**
 *
 * @author Allard van Altena
 */
public class ProjectOutput {
    private final LinkedHashMap<String, Object> OUTPUT_MAP = new LinkedHashMap<String, Object>();
    
    public ProjectOutput() {}
    
    public boolean outputExists(String projectName) {
    	return _checkCSVFile(projectName);
    }
    
    public boolean initOutput(String projectName) {
    	Logger.log("initOutput", 3);
    	
        if (_checkCSVFile(projectName)) {
	        EnergyMap processed = _getCSV(projectName);
	        
	        if (processed.getInitialised()) {
		        _addToMap("table", processed.getEnergyMap());
		        _addToMap("graph", processed.getEnergyListForFlot());
		        _addToMap("compound_count", processed.getLigandCount());
		        
		        return true;
	        }
        }
        
        return false;
    }
    
    public LinkedHashMap<String, Object> getMap() {
        return OUTPUT_MAP;
    }
    
    private EnergyMap _getCSV(String name) {
        FileCheck csvFile = new FileCheck();
        EnergyMap energyMap = new EnergyMap();
        
        File[] csvFiles = csvFile.getFilesWithExtension(VarConfig.getOutputUnzipLocation(name), VarConfig.getOutputCSVExt());
        
        if(csvFiles != null && csvFiles.length > 0) {        
            energyMap.initEnergyMapping(csvFiles[0].getPath());
        }
        
        return energyMap;
    }
    
    private void _addToMap(String name, Object object) {
        OUTPUT_MAP.put(name, object);
    }
    
    private boolean _checkCSVFile(String projectName) {
        FileCheck fileExists = new FileCheck();
        
        // Check if csv exists
        if (!fileExists.checkIfFilesWithExtensionExists(VarConfig.getOutputUnzipLocation(projectName), VarConfig.getOutputCSVExt())) {
        	// CSV does not exist
        	
        	// Check if output file exists
        	if (fileExists.checkIfFilesWithExtensionExists(VarConfig.getProjectFilePath(projectName), VarConfig.getOutputExt())) {
        		// Output file does not exist
        		// Unpack it
        		Unzipper unzip = new Unzipper();
                
                // unpack project output folder
                if (unzip.untar(VarConfig.getOutputUnzipLocation(projectName), VarConfig.getOutputFilePath(projectName))) {
                	
                	// CSV exists
                	return true;
                }
        	}
            
            // CSV does not exist
            return false;
        }
        
        // CSV exists
        return true;
    }
}