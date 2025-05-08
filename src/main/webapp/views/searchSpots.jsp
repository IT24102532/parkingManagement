<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Park.me - Search </title>
    <link rel="stylesheet" href="../assets/css/dual_container_global.css">
    <link rel="stylesheet" href="../assets/css/serchSpots.css">
</head>
<body>
<div class="container">
    <div class="left">

        <h1>search spots</h1>
        <form class="form" action="" method="post">
            <div class="form-group">
                <label for="start-date">start date</label>
                <input type="date" id="start-date" name="start-date" class="input">
            </div>

            <div class="form-group">
                <label for="end-date">end date</label>
                <input type="date" id="end-date" name="end-date" class="input">
            </div>

            <div class="form-group">
                <label for="vehicle-type">vehicle type</label>
                <div class="select-wrapper">
                    <select id="vehicle-type" name="vehicle-type" class="input">
                        <option value="" selected disabled hidden>Select vehicle type</option>
                        <option value="sedan">Sedan</option>
                        <option value="suv">SUV</option>
                        <option value="sports">Sports Car</option>
                        <option value="truck">Truck</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="location">location</label>
                <div class="select-wrapper">
                    <select id="location" name="location" class="input">
                        <option value="" selected disabled hidden>Select location</option>
                        <option value="downtown">Downtown</option>
                        <option value="airport">Airport</option>
                        <option value="suburbs">Suburbs</option>
                        <option value="mall">Shopping Mall</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="search-btn">search</button>
        </form>

    </div>
    <div class="right">

        <div class="right">
            <h1>LAX airport 2nd Terminal</h1>
            <div style="display: flex; gap: 10px;">
                <div class="match-box">L1 <span>-5%</span></div>
                <div class="match-box">L2 <span>No Pr</span></div>
                <div class="match-box">R1 <span>-5%</span></div>
                <div class="match-box">R3 <span>-5%</span></div>
            </div>
            <div style="margin-top: 20px;">
                <span style="color: #7A40F2; font-weight: bold; font-size: 20px;">$80</span>
                <span> / including taxes and fees</span>
            </div>
            <button class="select-btn">select</button>
        </div>

    </div>
</div>
</body>
</html>