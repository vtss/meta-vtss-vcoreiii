This README file contains information on building the meta-vcoreiii
BSP layer.

The source for this Yocto layer is:

  URI: git://github.com/vtss/meta-vtss-vcoreiii.git

Please see the corresponding sections below for details.

Refer to [NEWS.md](NEWS.md) for release information.

Dependencies
============

This layer depends on:

  URI: git://git.yoctoproject.org/poky.git
  layers: meta meta-yocto meta-yocto-bsp (default poky layers)
  branch: dizzy

The BSP has been tested on Yocto Project 1.7 - Dizzy. Other releases
may work, but this is not guaranteed.

The BSP provide a 3.14.x Linux kernel (currently the latest kernel.org
longterm release), and include architecture support and platform
drivers for the MIPS 24KEc processor present in most of the VTSS
switching chipsets. Furthermore, UnionFS support has been merged to
provide a robust, writable root file system basing upon SquashFS and
JFFS2 on the avaiable board platforms.

Patches
=======

Please submit any patches against this BSP to the maintainer:

Maintainer: Lars Povlsen <lpovlsen@vitesse.com>

Table of Contents
=================

  I. Building the meta-vcoreiii BSP layer
 II. Booting the images in /binary

I. Building the meta-vcoreiii BSP layer
========================================

In order to build an image with BSP support for a given release, you
need to download the corresponding BSP tarball from the 'Board Support
Package (BSP) Downloads' page of the Yocto Project website - or use
'git' to clone the poky repository.

Having done that, and assuming you extracted the BSP tarball contents
at the top-level of your yocto build tree, you can build a
vcoreiii image by adding the location of the meta-vcoreiii
layer to bblayers.conf, along with any other layers needed (to access
common metadata shared between BSPs) e.g.:

  poky/meta-xxxx \
  poky/meta-vcoreiii \

The supported machine targets are:

MACHINE setting | Target board(s)
--------------- | ---------------
luton10         | VSC7424EV, VSC7428EV
luton26         | VSC5610EV, VSC5611EV
jaguar1-cu24    | VSC5606EV
jaguar1-cu48    | VSC5608EV
serval1         | VSC5616EV, VSC5617EV, VSC5618EV, VSC5619EV
serval2         | VSC5629EV
jaguar2-cu24    | VSC5628EV
jaguar2-cu48    | VSC5627EV

To enable the vcoreiii layer, add the appropriate MACHINE to local.conf (f.ex):

> MACHINE ?= "jaguar2-cu24"

You should then be able to build a flash image as such:

  $ source oe-init-build-env build_<target>
  $ bitbake core-image-minimal

After this completes, you should find the images in
"tmp/deploy/images". There will be a "vmlinux-3.14.<machine>.gz"
(kernel) and a "core-image-minimal-<machine>.squashfs-xz" (root fs)
file.

Refer to the next section to learn how to install the images on the
target boards.

II. Booting the images
======================

The flash images should be put into the RedBoot FIS sections `linux`
and `root`, respetively. You can use normal redboot "fis" commands for
this - or you can use the WebStax system as described in AN1125:
"Vitesse Yocto Linux BSP".

The flash images need to be placed in SPI NOR flash in the locations
below:

MACHINE         |   Kernel   |    Root    | RootSize | Aux.Data |
-------         | ---------- | ---------- | -------- | -------- |
luton10         | 0x40800000 | 0x40A00000 |     6 MB |    NAND  |
luton26         | 0x40800000 | 0x40A00000 |     6 MB |    NAND  |
jaguar1-cu24    | 0x40800000 | 0x40A00000 |     6 MB |    NAND  |
jaguar1-cu48    | 0x40800000 | 0x40A00000 |     6 MB |    NAND  |
serval1         | 0x40880000 | 0x40A80000 |   4.5 MB |  SD/MMC  |
serval2         | 0x40E00000 | 0x41000000 |    10 MB |     NOR  |
jaguar2-cu24    | 0x40E00000 | 0x41000000 |    10 MB |     NOR  |
jaguar2-cu48    | 0x40E00000 | 0x41000000 |    10 MB |     NOR  |

The kernel is assumed to reserve 2 MB in all configurations. The root
file system in the NOR flash is mounted read-only, but augmented with
a writable flash file system located accordingly to the "Aux.Data"
column above. 

* If NAND is available, a JFFS2 file system overlay the root file
system. Typically 256MB is available in NAND.

* If NOR is used, the remainder of the root filesystem is used as a
JFFS2 file system overlay. The size of the writable section depend on
the size of the genererated root file system image.

* If SD/MMC is used (`serval1`), the system *must* be fitted with an
EXT2 formatted SD/MMC card of at least 4MB size prior to booting. If
not, the system will generate warning messages and the root file
system will be mounted read-only. (But the system will still boot).

After having flashed you system you can boot the system from RedBoot:

> fis load -d linux
> exec

