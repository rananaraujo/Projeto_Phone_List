using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace AgendaAPI.Models
{
    public class Contato
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string Name { get; set; }

        public string Apelido { get; set; }

        [Required]
        public string Number { get; set; }

        public string CPF { get; set; }

        [EmailAddress]
        public string Email { get; set; }

        public DateTime TimeCadastro { get; set; }

        public DateTime TimeUpdate { get; set; }
        
        public int UsuarioId { get; set; }
        
        [ForeignKey("UsuarioId")]
        public Usuario Usuario { get; set; }
    }
}