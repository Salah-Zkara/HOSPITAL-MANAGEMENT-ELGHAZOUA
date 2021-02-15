# HOSPITAL-MANAGEMENT
**GUI version**
- [HOSPITAL-MANAGEMENT](#HOSPITAL-MANAGEMENT)
  * [Download](#download)
  * [Requirements](#requirements)
    + [Install Java](#install-java)
    + [MongoDB setup](#MongoDB-setup)
  * [Execution](#execution)
    + [GUI](#gui)
      - [ Windows](#--windows-(cmd))
      - [ Linux](#--linux-(terminal))
  * [Links](#links)
## Download
To Download this software clone this repository or download it as a zip.
```bash
git clone https://github.com/Salah-Zkara/HOSPITAL-MANAGEMENT.git
cd HOSPITAL-MANAGEMENT
```

## Requirements
### Install Java
For this software, you need to have [Java SE Development Kit 11.0.10 (LTS)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) installed on your system.


### MongoDB setup
The Best way to use this software is to use [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) free tier (500Mo is more than enough for this software), otherwise, you can use a local MongoDB database.
- First of all, you have to create an account in the [cloud MongoDB platform](https://www.mongodb.com/cloud/atlas/register)
- Choose the free plan

![free plan](https://github.com/Salah-Zkara/HOSPITAL-MANAGEMENT/blob/main/src/Images/1.png)

- Choose the convenient Cloud Provider & Region

![Cloud Provider & Region](https://github.com/Salah-Zkara/HOSPITAL-MANAGEMENT/blob/main/src/Images/2.png)
- Click connect and fill in the required information --> connect your application and copy the connection string.

![Connection String](https://github.com/Salah-Zkara/HOSPITAL-MANAGEMENT/blob/main/src/Images/7.png)

- paste your database informations in **"./connection.txt"** like the follow: **"mongodb+srv://test:<password>@cluster0.6sjns.mongodb.net/<dbname>?retryWrites=true&w=majority"**.
- replace **<password>** with your **password** and **<dbname>** with your **database name**.

## Execution


### GUI
#### - Windows (cmd)
`$ java -jar HOSPITAL-MANAGEMENT.jar`

#### - Linux (terminal)
`$ java -jar HOSPITAL-MANAGEMENT.jar`


## Links
[![](https://img.shields.io/badge/My-Portfolio-brightgreen)](https://salah-zkara.codes/)

[![](https://img.shields.io/badge/-Linkedin-%232867B2)](https://www.linkedin.com/in/salah-eddine-zkara-b40b091a6/)
[![](https://img.shields.io/badge/-Facebook-%234267B2)](https://www.facebook.com/salaheddine.zkara.9)
[![](https://img.shields.io/badge/-Twitter-%231DA1F2)](https://twitter.com/SalahZkara)
[![](https://img.shields.io/badge/-Github-333)](https://github.com/Salah-Zkara)
[![](https://img.shields.io/badge/-Instagram-%23E1306C)](https://www.instagram.com/salaheddine.zkara/?hl=en)

![GitHub followers](https://img.shields.io/github/followers/Salah-Zkara?style=social)
#### Supervisors: 
- **Benkirane Said(GUI)**
- **Ziad Lamiae(Databases)**
- **Guezaz Azidine(Modeling)**
