using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AgendaAPI.Data;
using AgendaAPI.Models;
using System.Security.Claims;
using System.ComponentModel.DataAnnotations;

namespace AgendaAPI.Controllers
{
    [Authorize]
    [Route("api/[controller]")]
    [ApiController]
    public class ContatosController : ControllerBase
    {
        private readonly AgendaContext _context;

        public ContatosController(AgendaContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Contato>>> GetContatos()
        {
            var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));
            return await _context.Contatos
                .Where(c => c.UsuarioId == userId)
                .ToListAsync();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Contato>> GetContato(int id)
        {
            var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));
            var contato = await _context.Contatos
                .FirstOrDefaultAsync(c => c.Id == id && c.UsuarioId == userId);
            return contato ?? (ActionResult<Contato>)NotFound();
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutContato(int id, Contato contato)
        {
            var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));

            if (id != contato.Id)
                return BadRequest();

            var existingContato = await _context.Contatos
                .FirstOrDefaultAsync(c => c.Id == id && c.UsuarioId == userId);

            if (existingContato == null)
                return NotFound();

            contato.UsuarioId = userId;
            contato.TimeUpdate = DateTime.Now;

            _context.Entry(contato).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ContatoExists(id))
                    return NotFound();
                throw;
            }

            return NoContent();
        }

        [HttpPost]
        public async Task<ActionResult<Contato>> PostContato([FromBody] ContatoInputModel input)
        {
            var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));

            var contato = new Contato
            {
                UsuarioId = userId,
                Name = input.Nome,
                Number = input.Numero,
                Apelido = input.Apelido,
                CPF = input.CPF,
                Email = input.Email,
                TimeCadastro = DateTime.Now
            };

            _context.Contatos.Add(contato);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetContato), new { id = contato.Id }, contato);
        }

        public class ContatoInputModel
        {
            [Required]
            public string Nome { get; set; }

            [Required]
            public string Numero { get; set; }
            
            public string Apelido { get; set; }

            public string CPF { get; set; }

            [EmailAddress]
            public string Email { get; set; }
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteContato(int id)
        {
            var userId = int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier));
            var contato = await _context.Contatos
                .FirstOrDefaultAsync(c => c.Id == id && c.UsuarioId == userId);

            if (contato == null)
                return NotFound();

            _context.Contatos.Remove(contato);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ContatoExists(int id)
        {
            return _context.Contatos.Any(e => e.Id == id);
        }
    }
}