# Deploy

## Settings

To run the files correctly, you need to add the following parameter to the Linux kernel settings:

**/etc/sysctl.d/20-opensearch.conf:**
```
vm.max_map_count = 262144
```
