
package com.avbravo.microcoregenesis.controller;

/*-
 * #%L
 * microstream-examples-payara-micro
 * %%
 * Copyright (C) 2019 - 2021 MicroStream Software
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import com.avbravo.microcoregenesis.model.Product;
import com.avbravo.microcoregenesis.repository.ProductRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;


@RequestScoped
@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController
{
	@Inject
	private ProductRepository repository;
	
	// TODO don't worried about pagination
	@GET
	@Operation(summary = "Get all products", description = "Returns all available items at the restaurant")
	@APIResponse(responseCode = "500", description = "Server unavailable")
	@APIResponse(responseCode = "200", description = "The products")
	@Tag(name = "BETA", description = "This API is currently in beta state")
	@APIResponse(description = "The products", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Collection.class, readOnly = true, description = "the products", required = true, name = "products")))
	public Collection<Product> getAll()
	{
		return this.repository.getAll();
	}
	
	@GET
	@Path("{id}")
	@Operation(summary = "Find a product by id", description = "Find a product by id")
	@APIResponse(responseCode = "200", description = "The product")
	@APIResponse(responseCode = "404", description = "When the id does not exist")
	@APIResponse(responseCode = "500", description = "Server unavailable")
	@Tag(name = "BETA", description = "This API is currently in beta state")
	@APIResponse(description = "The product", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Product.class)))
	public Product findById(
		@Parameter(description = "The item ID", required = true, example = "1", schema = @Schema(type = SchemaType.INTEGER)) @PathParam("id") final long id)
	{
		return this.repository.findById(id).orElseThrow(
			() -> new WebApplicationException("There is no product with the id " + id, Response.Status.NOT_FOUND));
	}
	
	@POST
	@Operation(summary = "Insert a product", description = "Insert a product")
	@APIResponse(responseCode = "201", description = "When creates an product")
	@APIResponse(responseCode = "500", description = "Server unavailable")
	@Tag(name = "BETA", description = "This API is currently in beta state")
	public Response insert(
		@RequestBody(description = "Create a new product.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))) final Product product)
	{
		return Response.status(Response.Status.CREATED).entity(this.repository.save(product)).build();
	}
	
	@DELETE
	@Path("{id}")
	@Operation(summary = "Delete a product by ID", description = "Delete a product by ID")
	@APIResponse(responseCode = "200", description = "When deletes the product")
	@APIResponse(responseCode = "500", description = "Server unavailable")
	@Tag(name = "BETA", description = "This API is currently in beta state")
	public Response delete(
		@Parameter(description = "The item ID", required = true, example = "1", schema = @Schema(type = SchemaType.INTEGER)) @PathParam("id") final long id)
	{
		this.repository.deleteById(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
}
