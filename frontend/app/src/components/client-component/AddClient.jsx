import {useState} from "react";
import axios from 'axios';
import ClientDao from "../../dao/ClientDao";
import "./styles.css";

const AddClient = () => {

    const [showForm, setShowForm] = useState(false);

    const [name, setName] = useState('Nombre');

    const [enterprise, setEnterprise] = useState('Empresa');

    const OpenForm = (e) => {

        e.preventDefault();

        setShowForm(true);
    }

    const NameChanged = (e) => {

        setName(e.target.value);
    }

    const EnterpriseChanged = (e) => {

        setEnterprise(e.target.value);
    }

    const AddClient = (e) => {

        e.preventDefault();

        if(name.toString()!=='' && name.toString().length >= 3 &&
            enterprise.toString()!=='' && enterprise.toString().length >= 3){

            // eslint-disable-next-line no-undef
            const client = new ClientDao(0 ,name, enterprise);

            //Headers
            const headers = {
                'Content-Type': 'application/json',
            };

            //Body, for be use in spring-boot side
            const content = {
                name : client.name,
                enterprise : client.enterprise
            };


            //Make the request and get the data for update result
            axios.post('http://localhost:8080/api/clients', content, {headers})
                .then(response =>  alert(response.data))
                .catch(error => {
                    alert('There was an error: ' + error);
                });

            setShowForm(false);

            setName('Nombre');

            setEnterprise('Empresa');

        }

        else{

            if(name.toString()===''){
                setName('Este campo no puede estar vacio.')
            }

            if(name.toString().length<3){
                setName('Este campo debe contener 3 o mas caracteres.')
            }

            if(enterprise.toString()===''){
                setEnterprise('Este campo no puede estar vacio.')
            }

            if(enterprise.toString().length<3){
                setEnterprise('Este campo debe contener 3 o mas caracteres.')
            }

            alert("¡Error en registro!")
        }


    }

    return (

        <div className="homepage">

            {/* Open dialog, with this button */}
            <button className={"buttonCard buttonAdd"} onClick={OpenForm}><i className="fa fa-plus"></i> Agregar</button>

            {/* When add button is clicked, show this popup */}
            {showForm && (
                <div className="center-div-container-form ">
                    <div className="specialForm">
                        <form onSubmit={AddClient}>

                            <input type="text" onChange={NameChanged} placeholder={name}/>

                            <input type="text" onChange={EnterpriseChanged} placeholder={enterprise}/>

                            <input type="submit" value="Agregar"/>

                        </form>
                    </div>

                </div>





            )}
        </div>


    )
}

export default AddClient;