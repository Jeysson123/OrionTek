import {useState} from "react";
import axios from 'axios';
import ClientDao from "../../dao/ClientDao";
import "./styles.css";
import DirectionDao from "../../dao/DirectionDao";

const AddDirection = (props) => {

    const [showForm, setShowForm] = useState(false);

    const [address, setAddress] = useState('Direccion');

    const OpenForm = (e) => {

        e.preventDefault();

        setShowForm(true);
    }

    const AddressChanged = (e) => {

        setAddress(e.target.value);
    }

    const NewDirection = (e) => {

        e.preventDefault();

        if(address.toString()!=='' && address.toString().length >= 3){

            const client = new ClientDao(props.id, props.name, props.enterprise);

            //Headers
            const headers = {
                'Content-Type': 'application/json',
            };

            //Body, for be use in spring-boot side
            const content = {
                name: client.name,
                id : client.id,
                enterprise : client.enterprise
            };


            //Make the request and get the data for update result
            axios.post('http://localhost:8080/api/directions?address='+address, content, {headers})
                .then(response =>  alert(response.data))
                .catch(error => {
                    alert('There was an error: ' + error);
                });

            setShowForm(false);


        }

        else{

            if(address.toString()===''){
                setAddress('Este campo no puede estar vacio.')
            }

            if(address.toString().length<3){
                setAddress('Este campo debe contener 3 o mas caracteres.')
            }

            alert("¡Error en insercion!")
        }


    }

    return (

        <div className="homepage">

            {/* Open dialog, with this button */}
            <button className={"buttonCard buttonAddDirection"} onClick={OpenForm}><i className="fa fa-plus"></i> Agregar</button>

            {/* When add button is clicked, show this popup */}
            {showForm && (
                 <div className="specialForm">
                        <form onSubmit={NewDirection}>

                            <input type="text" onChange={AddressChanged} placeholder={address}/>

                            <input type="submit" value="Agregar"/>

                        </form>
                    </div>






            )}
        </div>


    )
}

export default AddDirection;