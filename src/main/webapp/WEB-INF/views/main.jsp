<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="ISO-8859-1">
        <title>Dashboard - Gestion des Brevets</title>

        <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
        <style type="text/css">
            .dashboard-container {
                padding: 10px;
                max-width: 1200px;
                margin: 0 auto;
            }

            .stats-cards {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 15px;
                margin-bottom: 15px;
            }

            .card {
                background: #fff;
                border-radius: 10px;
                padding: 15px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
                text-align: center;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                border: 1px solid #eee;
                cursor: pointer;
            }

            .card:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            }

            .card-link {
                text-decoration: none !important;
                color: inherit !important;
                display: block;
            }

            .card-link:hover {
                text-decoration: none !important;
            }

            .card .icon {
                font-size: 24px;
                margin-bottom: 10px;
                color: #4e73df;
            }

            .card h3 {
                margin: 0;
                font-size: 12px;
                color: #888;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                font-weight: 600;
            }

            .card .value {
                font-size: 28px;
                font-weight: 800;
                margin: 5px 0;
                color: #2c3e50;
            }

            .charts-container {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(450px, 1fr));
                gap: 20px;
            }

            @media (max-width: 500px) {
                .charts-container {
                    grid-template-columns: 1fr;
                }
            }

            .chart-box {
                background: #fff;
                border-radius: 10px;
                padding: 15px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
                border: 1px solid #eee;
                display: flex;
                flex-direction: column;
            }

            .chart-box h4 {
                margin-top: 0;
                margin-bottom: 10px;
                text-align: left;
                color: #2c3e50;
                font-size: 16px;
                font-weight: 600;
                border-bottom: 2px solid #f8f9fa;
                padding-bottom: 8px;
            }

            .chart-wrapper {
                position: relative;
                height: 240px;
                width: 100%;
            }
        </style>
    </head>

    <body>
        <div class="dashboard-container">
            <div class="stats-cards">
                <a href="brevets" class="card-link">
                    <div class="card">
                        <div class="icon"><i class="fa fa-certificate"></i></div>
                        <h3>Total Brevets</h3>
                        <div class="value" id="totalBrevets">-</div>
                    </div>
                </a>
                <a href="inventions" class="card-link">
                    <div class="card">
                        <div class="icon"><i class="fa fa-lightbulb-o"></i></div>
                        <h3>Total Inventions</h3>
                        <div class="value" id="totalInventions">-</div>
                    </div>
                </a>
                <a href="inventeurs" class="card-link">
                    <div class="card">
                        <div class="icon"><i class="fa fa-users"></i></div>
                        <h3>Total Inventeurs</h3>
                        <div class="value" id="totalInventeurs">-</div>
                    </div>
                </a>
                <a href="enterprises" class="card-link">
                    <div class="card">
                        <div class="icon"><i class="fa fa-building"></i></div>
                        <h3>Total Entreprises</h3>
                        <div class="value" id="totalEntreprises">-</div>
                    </div>
                </a>
                <a href="domaines" class="card-link">
                    <div class="card">
                        <div class="icon"><i class="fa fa-tags"></i></div>
                        <h3>Total Domaines</h3>
                        <div class="value" id="totalDomaines">-</div>
                    </div>
                </a>
            </div>

            <div class="charts-container">
                <div class="chart-box">
                    <h4>Brevets par Entreprise</h4>
                    <div class="chart-wrapper">
                        <canvas id="inventionParEntreprise"></canvas>
                    </div>
                </div>
                <div class="chart-box">
                    <h4>Inventions par Domaine</h4>
                    <div class="chart-wrapper">
                        <canvas id="inventionParDomanine"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            const colors = [
                '#4e73df', '#1cc88a', '#36b9cc', '#f6c23e', '#e74a3b',
                '#5a5c69', '#6610f2', '#6f42c1', '#e83e8c', '#fd7e14'
            ];

            const Http = new XMLHttpRequest();
            const url = 'ChartController';
            Http.open("GET", url);
            Http.send();

            Http.onload = (e) => {
                if (Http.status !== 200) return;

                let dataRecieved = JSON.parse(Http.responseText);

                // Update stats
                document.getElementById('totalBrevets').innerText = dataRecieved.totalBrevets;
                document.getElementById('totalInventions').innerText = dataRecieved.totalInventions;
                document.getElementById('totalInventeurs').innerText = dataRecieved.totalInventeurs;
                document.getElementById('totalEntreprises').innerText = dataRecieved.totalEntreprises;
                document.getElementById('totalDomaines').innerText = dataRecieved.totalDomaines;

                let inventionParDomaines = dataRecieved.inventionParDomaines;
                let inventionParEntreprise = dataRecieved.inventionParEntreprises;

                // Chart Invention Par Entreprise
                const ctx = document.getElementById('inventionParEntreprise').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: inventionParEntreprise.map(e => e.Entreprise),
                        datasets: [{
                            label: 'Nombre de brevets',
                            data: inventionParEntreprise.map(e => e.nbInventions),
                            backgroundColor: colors.slice(0, inventionParEntreprise.length),
                            borderRadius: 6,
                            borderWidth: 0
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: { display: false }
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                grid: { drawBorder: false, color: '#f8f9fa' }
                            },
                            x: {
                                grid: { display: false }
                            }
                        }
                    }
                });

                // Chart Invention Par Domaine
                const chatInventionParDomanine = document.getElementById('inventionParDomanine').getContext('2d');
                new Chart(chatInventionParDomanine, {
                    type: 'doughnut',
                    data: {
                        labels: inventionParDomaines.map(e => e.Domaine),
                        datasets: [{
                            data: inventionParDomaines.map(e => e.nbInventions),
                            backgroundColor: colors.slice(0, inventionParDomaines.length),
                            hoverOffset: 4,
                            borderWidth: 2,
                            borderColor: '#ffffff'
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'bottom',
                                labels: { usePointStyle: true, padding: 20 }
                            }
                        },
                        cutout: '70%'
                    }
                });
            }
        </script>
    </body>

    </html>