import './styles.css';
import img from "../../imgs/client.png";
import ClientDao from "../../dao/ClientDao";
import axios from "axios";
import {useState} from "react";
import DirectionDao from "../../dao/DirectionDao";


const Direction = (props) => {

    const [showFormUpdate, setShowFormUpdate] = useState(false);

    const [address, setAddress] = useState(props.address);


    const AddressChanged = (e) => {
        setAddress(e.target.value);
    };


    const OpenFormUpdate = (e) => {
        e.preventDefault();
        setShowFormUpdate(true);
    };

    const UpdateDirection = (e) => {

        e.preventDefault();

        if(props.address.toString()!=='' && props.address.toString().length >= 3
            ){

            const direction = new DirectionDao(props.id , address, props.client_id);

            //Headers
            const headers = {
                'Content-Type': 'application/json',
            };

            //Body, for be use in spring-boot side
            const content = {
                address : direction.address,
                id: direction.id,
                client_id: props.client
            };


            //Make the request and get the data for update result
            axios.put('http://localhost:8080/api/directions/refresh/'+direction.id, content, {headers})
                .then(response =>  alert(response.data))
                .catch(error => {
                    alert('There was an error: ' + error);
                });

            setShowFormUpdate(false);
        }

        else{

            if(props.address.toString()===''){
                setAddress('Este campo no puede estar vacio.')
            }

            if(props.address.toString().length<3){
                setAddress('Este campo debe contener 3 o mas caracteres.')
            }

            alert("¡Error al actualizar!")
        }


    };

    const DeleteDirection = (e) => {

        e.preventDefault();

        const direction = new DirectionDao(props.id,'', '');

        //Make the request and get the data for delete result
        axios.delete('http://localhost:8080/api/directions/remove/'+direction.id)
            .then(response =>  alert(response.data))
            .catch(error => {
                alert('There was an error: ' + error);
            });


    };


    return (

        <div className="card">
            <div className="row">

                    <div className="container">

                        <p>{props.address}</p>

                        {/* When UPDATE button is clicked, show this popup */}
                        {showFormUpdate && (
                                <form onSubmit={UpdateDirection}>
                                    <input type="text" onChange={AddressChanged} placeholder={address} />
                                    <input type="submit" value="Actualizar" />
                                </form>
                        )}
                    </div>
                </div>
                <button className={"buttonCard buttonUpdateDirection"} onClick={OpenFormUpdate}>
                    <i className="fa fa-pencil"></i> Actualizar
                </button>
                <button className={"buttonCard buttonDeleteDirection"} onClick={DeleteDirection}>
                    <i className="fa fa-trash"></i> Eliminar
                </button>

        </div>



    )
}


export default Direction;