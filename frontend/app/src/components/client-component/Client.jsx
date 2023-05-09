import "./styles.css";
import img from "../../imgs/client.png";
import { useState } from "react";
import ClientDao from "../../dao/ClientDao";
import axios from "axios";
import Direction from "../directions-component/Direction";
import AddClient from "./AddClient";
import AddDirection from "../directions-component/AddDirection";

const Client = (props) => {
    const [showFormUpdate, setShowFormUpdate] = useState(false);
    const [name, setName] = useState("Nombre");
    const [enterprise, setEnterprise] = useState("Empresa");
    const [directions, setDirections] = useState([]);
    const [showDirections, setShowDirections] = useState(false);

    const OpenFormUpdate = (e) => {
        e.preventDefault();
        setShowFormUpdate(true);
        setShowDirections(false);
    };

    const NameChanged = (e) => {
        setName(e.target.value);
    };

    const EnterpriseChanged = (e) => {
        setEnterprise(e.target.value);
    };

    const UpdateClient = (e) => {

        e.preventDefault();

        if(name.toString()!=='' && name.toString().length >= 3 &&
            enterprise.toString()!=='' && enterprise.toString().length >= 3){

            const client = new ClientDao(props.id ,name, enterprise);

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
            axios.put('http://localhost:8080/api/clients/refresh/'+client.id, content, {headers})
                .then(response =>  alert(response.data))
                .catch(error => {
                    alert('There was an error: ' + error);
                });

            setName('Nombre');

            setEnterprise('Empresa');

            setShowFormUpdate(false);
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


    };

    const DeleteClient = (e) => {

        e.preventDefault();

        const client = new ClientDao(props.id,'', '');

        //Make the request and get the data for delete result
        axios.delete('http://localhost:8080/api/clients/remove/'+client.id)
                .then(response =>  alert(response.data))
                .catch(error => {
                    alert('There was an error: ' + error);
                });


    };

    const ShowDirectionsClient = (e) => {

        e.preventDefault();

        setShowFormUpdate(false);

        //Headers
        const headers = {
            'Content-Type': 'application/json',
        };


        axios.get(`http://localhost:8080/api/directions/check?id=${props.id}`, {headers})
            .then(response => {
                const parsedCDirections = response.data.map(item => ({id: item.id, address: item.address}));
                setDirections(parsedCDirections);
                setShowDirections(true);
            })
            .catch(error => console.log(error));
    };



    return (
        <div className="card">
            <img src={img} alt="Avatar" />
            <div className="row">
                <div className="column">
                    <div className="container">
                        <h4>
                            <b>{props.name}</b>
                        </h4>
                        <p>{props.enterprise}</p>

                        {/* When UPDATE button is clicked, show this popup */}
                        {showFormUpdate && (
                                <div className="specialForm">
                                    <form onSubmit={UpdateClient}>
                                        <input type="text" onChange={NameChanged} placeholder={props.name} />
                                        <input
                                            type="text"
                                            onChange={EnterpriseChanged}
                                            placeholder={props.enterprise}
                                        />
                                        <input type="submit" value="Actualizar" />
                                    </form>
                                </div>
                        )}
                    </div>
                </div>
            </div>

            {/* When DIRECTION button is clicked, show this popup */}
            {showDirections && (
                <div className="specialForm">
                    <AddDirection id={props.id} name={props.name} enterprise={props.enterprise}></AddDirection>
                    <h4>Direcciones</h4>
                    {directions && directions.length > 0 ? directions.map((item) =>
                            <Direction id={item.id} address={item.address} client_id={props.id}/>)
                        : null

                    }
                </div>
            )}

            <button className={"buttonCard buttonUpdate"} onClick={OpenFormUpdate}>
                <i className="fa fa-pencil"></i> Actualizar
            </button>
            <button className={"buttonCard buttonDelete"} onClick={DeleteClient}>
                <i className="fa fa-trash"></i> Eliminar
            </button>
            <button className={"buttonCard buttonDirections"} onClick={ShowDirectionsClient}>
                <i className="fa fa-arrow-up"></i> Direcciones
            </button>

        </div>
    );
};

export default Client;
