<?php
    date_default_timezone_set("Asia/Jakarta");
    include("config.php");
    session_start();
    ob_start();
    $key = "aplikasi-pickpack";
    $response = array();
    if (isset($_POST['function'])){
        $function = $_POST['function'];
        $userkey = $_POST['key'];
        if($userkey==$key){
            if($function=="ListOrders"){
                $id_user = $_POST['id_user'];
                $sql = "SELECT o.*, GROUP_CONCAT(b.name) as bin_name FROM tbl_order o LEFT JOIN tbl_bin b ON b.id_bin = o.bin WHERE o.id_user = '$id_user' AND o.status = 'ORDER' GROUP BY o.so ORDER BY o.order_time ASC";
                $picking = 0;
                $packing = 0;
                $orders = 0;
                if ($result=mysqli_query($db,$sql)){
                    while ($row=mysqli_fetch_array($result,MYSQLI_ASSOC)){
                        $listIsi = array();
                        $listIsi['id_order'] = $row['id_order'];
                        $listIsi['warehouse_code'] = $row['warehouse_code'];
                        $listIsi['tenant_code'] = $row['tenant_code'];
                        $listIsi['so'] = $row['so'];
                        $listIsi['status'] = $row['status'];
                        $listIsi['courier'] = $row['courier'];
                        $listIsi['mcode'] = $row['mcode'];
                        $listIsi['description'] = $row['description'];
                        $listIsi['bin'] = $row['bin'];
                        $listIsi['id_user'] = $row['id_user'];
                        $listIsi['order_qty'] = $row['order_qty'];
                        $listIsi['picked_qty'] = $row['picked_qty'];
                        $listIsi['order_time'] = $row['order_time'];
                        $listIsi['picking_time'] = $row['picking_time'];
                        $listIsi['packing_time'] = $row['packing_time'];
                        $listIsi['complete_time'] = $row['complete_time'];
                        $listIsi['id_user'] = $row['id_user'];
                        $listIsi['bin_name'] = $row['bin_name'];
                        $response[] = $listIsi;
                    }
                    mysqli_free_result($result);
                }
                $result = array();
                $result['hasil'] = $response;
                print(json_encode($result));
            }
        }else{
            // no function
            $response["success"] = 0;
            $response["message"] = "Function missing";
            print(json_encode($response));    
        } 
    }else{
        // no function
        $response["success"] = 0;
        $response["message"] = "Function missing";
        print(json_encode($response));
    }
?> 