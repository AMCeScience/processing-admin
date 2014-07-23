processing-admin
================

Administration portlet for processing manager, implements webservice functions of the processing manager to provide administration for computation projects running on the grid. Data has to be stored in the nsgdm data catalog format in order for this portlet to work properly.

In order for the configuration class (VarConfig) to work add a .json format file anywhere on your server and add the internal file path to the VarCon$

```JSON
{
        "variable_name": "variable_value",
        "other_name": true,
        "another_name": 10
}
```
