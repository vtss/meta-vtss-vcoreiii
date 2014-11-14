# Version : vtss_1.10
## Date: 2014-10-20

Description: Maintenance release, supporting all VTSS reference
boards.

New Features
============

* New boards (MACHINE) types added, full list:

  * `luton10`      (new)
  * `luton26`
  * `serval1`
  * `jaguar1-cu24` (previously named `jaguar1`)
  * `jaguar1-cu48` (new)
  * `serval2`      (new)
  * `jaguar2-cu24` (new)
  * `jaguar2-cu48` (new)

* SD/MMC support on Serval1/Serval2.

* Kernel updated to 3.14.20 LTS from kernel.org

Important Changes
=================

* `serval1` flash layout has changed. Refer to the
  [README.md](README.md) file or the _AN1125: Vitesse Linux BSP
  (Yocto)_ application note for more details. Unless the `root`
  partition is properly placed, the system will fail to boot.

* `serval1` requires a `EXT2` formatted SD/MMC card in order to mount
  the root file system as read/write for normal operation. Refer to
  `mkfs(8)`, using `-t ext2` as the file system type.

Fixed Problems
==============

* A number of warning messages during boot have been fixed. The
  messages did not have any effect on the functionality of the system.

Known Problems
==============

*None.*

---------------------------------------------------------------------------

# Version : vtss_1.00
## Date: 2014-07-21

Description: Initial Release
