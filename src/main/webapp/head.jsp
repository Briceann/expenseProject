<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 3/9/25
  Time: 12:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="taglib.jsp"%>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Material Icons (optional) -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- jQuery (only needed for DataTables) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Bootstrap 5 JS Bundle (includes Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- DataTables Bootstrap 5 -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css">
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>

    <style>
        html, body {
            height: 100%;
            margin: 0;
            background-color: #121212;
            color: #fff;
        }

        .wrapper {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .content {
            flex: 1;
        }
        .site-footer {
            background-color: #1a1a1a;
            color: #ccc;
            font-size: 0.9rem;
            padding: 1rem 0;
        }

        .site-footer a {
            color: #ccc;
            text-decoration: none;
        }

        .site-footer a:hover {
            color: #fff;
            text-decoration: underline;
        }

        /* Dashboard specific styling */
        .card {
            background-color: #1e1e1e;
            color: #fff;
            border: none;
            border-radius: 12px;
        }
        .section-title {
            margin-top: 2rem;
            margin-bottom: 1rem;
        }

        /* View Expense table and form styles */

        .table-dark thead th {
            background-color: #1f1f1f;
            color: #fff;
        }

        .table td, .table th {
            vertical-align: middle;
        }

        .btn-outline-primary, .btn-outline-secondary {
            border-color: #666;
            color: #ccc;
        }

        .btn-outline-primary:hover,
        .btn-outline-secondary:hover {
            background-color: #0d6efd;
            color: white;
        }

        .btn-danger, .btn-primary {
            padding: 4px 10px;
            font-size: 0.875rem;
        }

        .form-select, .form-control {
            background-color: #1e1e1e;
            color: #fff;
            border-color: #333;
        }

        /* DataTables dark mode fixes */
        .dataTables_wrapper .dataTables_length,
        .dataTables_wrapper .dataTables_filter,
        .dataTables_wrapper .dataTables_info,
        .dataTables_wrapper .dataTables_paginate {
            color: #f1f1f1;
        }

        .dataTables_wrapper .dataTables_filter input {
            background-color: #fff;
            color: #000;
            border: 1px solid #ccc;
        }

        .dataTables_wrapper .dataTables_length select {
            background-color: #1e1e1e;
            color: #fff;
            border: 1px solid #444;
        }

        label.form-label {
            color: #f1f1f1;
            font-weight: 500;
        }
    </style>
</head>
